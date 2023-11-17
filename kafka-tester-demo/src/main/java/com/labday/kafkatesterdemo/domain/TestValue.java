package com.labday.kafkatesterdemo.domain;

public class TestValue
{
    private String value;

    public TestValue(final String value)
    {
        this.value = value;
    }

    public TestValue()
    {
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(final String value)
    {
        this.value = value;
    }
}
