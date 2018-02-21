package featureExtraction.interfaces;

public interface ARFFPaths {
   public abstract String getNormalPath();
   
   public abstract String getSlowBodyPath();
   public abstract String getSlowHeadersPath();
   public abstract String getSlowReadPath();
   public abstract String getTCPFloodPath();
   public abstract String getUDPFloodPath();
   public abstract String getHTTPFloodPath();
   
   public abstract String getNoiseSlowBodyPath();
   public abstract String getNoiseSlowHeadersPath();
   public abstract String getNoiseSlowReadPath();
   public abstract String getNoiseTCPFloodPath();
   public abstract String getNoiseUDPFloodPath();
   public abstract String getNoiseHTTPFloodPath();
   
   public abstract String getKDDTrainPath() throws UnsupportedOperationException;
}
