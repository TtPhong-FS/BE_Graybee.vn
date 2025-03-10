package vn.graybee.projections.collections;

public interface CpuDetailProjection {

    long getProductId();

    String getSocket();

    int getMultiplier();

    int getNumberOfStreams();

    float getMaximumPerformanceCore();

    float getMaximumEfficiencyCore();

    float getBasePerformanceCore();

    float getBaseEfficiencyCore();

    float getPowerConsumption();

    int getCache();

    String getMotherboardCompatible();

    int getMaximumBandwidth();

    String getMemoryType();

    boolean isGraphicsCore();

}
