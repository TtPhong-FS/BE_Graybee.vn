package vn.graybee.projections.collections;

public interface MouseDetailProjection {

    long getProductId();

    String getSensors();

    int getNumberOfNodes();

    String getSwitchType();

    String getSwitchLife();

    int getPollingRate();

    String getSoftware();

    String getConnect();

    boolean isWirelessConnect();

    String getBattery();

    String getLed();

}
