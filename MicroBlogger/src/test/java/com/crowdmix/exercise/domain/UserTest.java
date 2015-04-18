package com.crowdmix.exercise.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class UserTest {

    @Test
    public void construction() {
        // Given
        String username = "Foo";

        // When
        User user = new User(username);

        // Then
        assertThat(user.getName(), is(username));
    }

    @Test
    public void equality() {
        // Given
        User user = new User("Foo");

        // Then
        assertThat(user, is(equalTo(user)));
        assertThat(user, is(equalTo(new User("Foo"))));
        assertThat(new User("Foo"), is(equalTo(user)));

        assertThat(user, not(equalTo(null)));
        assertThat(user, not(equalTo(new Object())));
        assertThat(user, not(equalTo(new User("foo"))));
        assertThat(user, not(equalTo(new User("Bar"))));
    }

    @Test
    public void hashCodeContract() {
        assertThat(new User("Foo").hashCode(), is(equalTo(new User("Foo").hashCode())));
        assertThat(new User("Bar").hashCode(), is(equalTo(new User("Bar").hashCode())));
        assertThat(new User(null).hashCode(), is(equalTo(new User(null).hashCode())));
    }
}
