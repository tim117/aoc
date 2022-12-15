package aoc.app;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class File {
    abstract String name();

    abstract int size();

    public static Builder builder() {
        return new AutoValue_File.Builder();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        abstract String name();

        abstract Builder name(String name);

        abstract Builder size(int size);

        abstract File build();
    }
}
