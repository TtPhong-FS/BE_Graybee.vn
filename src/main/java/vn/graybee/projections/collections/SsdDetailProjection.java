package vn.graybee.projections.collections;

public interface SsdDetailProjection {

    long getProductId();

    String getCommunicationStandard();

    int getCapacity();

    int getLifeSpan();

    int getReadingSpeed();

    int getWritingSpeed();
    
    String getStorageTemperature();

    String getOperatingTemperature();

    int getRandomReadingSpeed();

    int getRandomWritingSpeed();

    String getSoftware();

}
