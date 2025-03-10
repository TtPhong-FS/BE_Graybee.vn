package vn.graybee.projections.collections;

public interface RamDetailProjection {

    long getProductId();

    String getSuitableFor();

    String getSeries();

    int getCapacity();

    String getType();

    int getSpeed();

    String getLatency();

    float getVoltage();
    
    boolean isHeatDissipation();

    String getLed();

}
