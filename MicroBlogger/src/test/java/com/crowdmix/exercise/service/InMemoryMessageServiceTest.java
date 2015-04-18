package com.crowdmix.exercise.service;

import com.crowdmix.exercise.domain.Message;
import com.crowdmix.exercise.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

import static java.time.Instant.now;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class InMemoryMessageServiceTest {

    @Mock private Clock clock;
    private MessageService service;

    @Before
    public void setUp() throws Exception {
        service = new InMemoryMessageService(clock);
    }

    @Test
    public void shouldBeginWithEmptyTimelineAndWall() {
        assertThat(service.getTimeline(new User("Foo")).size(), is(0));
        assertThat(service.getWall(new User("Foo")).size(), is(0));
    }

    @Test
    public void timelineShouldContainMessagesPostedByTheUser() {
        // Given
        Instant now = now();

        User userOne = new User("Foo");
        User userTwo = new User("Bar");

        given(clock.instant()).willReturn(now.minusSeconds(5), now.minusSeconds(3), now);

        service.publish(userOne, "Hello, world!");
        service.publish(userTwo, "Good morning..");
        service.publish(userOne, "I love social networking!!");

        // When
        List<Message> userOnesTimeline = service.getTimeline(userOne);
        List<Message> userTwosTimeline = service.getTimeline(userTwo);

        // Then
        assertThat(userOnesTimeline.size(), is(2));
        assertThat(userTwosTimeline.size(), is(1));

        assertMessage(userOnesTimeline.get(0), userOne, "Hello, world!", now.minusSeconds(5));
        assertMessage(userOnesTimeline.get(1), userOne, "I love social networking!!", now);

        assertMessage(userTwosTimeline.get(0), userTwo, "Good morning..", now.minusSeconds(3));
    }

    @Test
    public void wallShouldContainMessagesPostedByTheUserAndTheirFolloweesInChronologicalOrder() {
        // Given
        Instant now = now();

        User user = new User("Foo");
        User followee = new User("Bar");

        given(clock.instant()).willReturn(now.minusSeconds(5), now.minusSeconds(3), now.minusSeconds(2), now);

        service.publish(followee, "Hello, world!");
        service.publish(user, "Good morning..");

        service.follow(user, followee);

        service.publish(followee, "I love social networking!!");
        service.publish(new User("Doe"), "This is awesome!");

        // When
        List<Message> wall = service.getWall(user);

        // Then
        assertThat(wall.size(), is(3));
        assertMessage(wall.get(0), followee, "Hello, world!", now.minusSeconds(5));
        assertMessage(wall.get(1), user, "Good morning..", now.minusSeconds(3));
        assertMessage(wall.get(2), followee, "I love social networking!!", now.minusSeconds(2));
    }

    @Test
    public void timelineShouldNotContainMessagesPostedByFollowees() {
        // Given
        User user = new User("Foo");
        User followee = new User("Bar");

        given(clock.instant()).willReturn(now());

        service.follow(user, followee);

        service.publish(user, "Hello, world!");
        service.publish(followee, "Good morning..");

        // When
        List<Message> timeline = service.getTimeline(user);

        // Then
        assertThat(timeline.size(), is(1));
        assertThat(timeline.get(0).getPublisher(), is(user));
    }

    @Test
    public void timelineShouldNotContainMessagesPostedByFollowers() {
        // Given
        User user = new User("Foo");
        User follower = new User("Bar");

        given(clock.instant()).willReturn(now());

        service.follow(follower, user);

        service.publish(user, "Hello, world!");
        service.publish(follower, "Good morning..");

        // When
        List<Message> timeline = service.getTimeline(user);

        // Then
        assertThat(timeline.size(), is(1));
        assertThat(timeline.get(0).getPublisher(), is(user));
    }

    @Test
    public void wallShouldNotContainMessagesPostedByFollowers() {
        // Given
        User user = new User("Foo");
        User follower = new User("Bar");

        given(clock.instant()).willReturn(now());

        service.follow(follower, user);

        service.publish(user, "Hello, world!");
        service.publish(follower, "Good morning..");

        // When
        List<Message> wall = service.getWall(user);

        // Then
        assertThat(wall.size(), is(1));
        assertThat(wall.get(0).getPublisher(), is(user));
    }

    private void assertMessage(Message actual, User expectedUser, String expectedText, Instant expectedTimestamp) {
        assertThat(actual.getPublisher(), is(expectedUser));
        assertThat(actual.getText(), is(expectedText));
        assertThat(actual.getTimestamp(), is(expectedTimestamp));
    }
}
