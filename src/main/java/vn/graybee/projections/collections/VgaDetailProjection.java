package vn.graybee.projections.collections;

public interface VgaDetailProjection {

    long getProductId();

    int getMemorySpeed();

    String getMemory();

    int getMemoryBus();

    int getCudaKernel();

    String getMaximumResolution();

    int getMaximumScreen();

    String getPorts();

    String getClock();

    int getPowerConsumption();

    int getPsuRecommend();

    boolean isApplicationSupport();

}
