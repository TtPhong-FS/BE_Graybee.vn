package vn.graybee.projections.collections;

public interface HddDetailProjection {

    long getProductId();

    String getCommunicationStandard();

    int getCapacity();

    int getHoursToFailure();

    int getReadingSpeed();

    int getWritingSpeed();

    float getNoiseLevel();

    int getCache();

}
