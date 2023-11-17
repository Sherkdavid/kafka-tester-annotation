package com.labday.kafkatesterdemo.domain;

public class TestKey
{
    private String firstName;
    private String surname;

    public TestKey(final String firstName, final String surname)
    {
        this.firstName = firstName;
        this.surname = surname;
    }

    public TestKey()
    {
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(final String firstName)
    {
        this.firstName = firstName;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(final String surname)
    {
        this.surname = surname;
    }
}
