package featureExtraction;

public interface FeatureExtractionPaths {
   public abstract String getNormalPath();
   public abstract String getSlowBodyPath();
   public abstract String getSlowHeadersPath();
   public abstract String getSlowReadPath();
   public abstract String getTCPFloodPath();
   public abstract String getUDPFloodPath();
   public abstract String getHTTPFloodPath();
}
