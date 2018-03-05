package featureExtraction.interfaces;

public interface ARFFPaths {
   public abstract String getNormalPath();
   
   public abstract String getSlowBodyPath();
   public abstract String getSlowHeadersPath();
   public abstract String getSlowReadPath();
   public abstract String getTCPFloodPath();
   public abstract String getUDPFloodPath();
   public abstract String getHTTPFloodPath();
   
   /** 
    * Noise normal = normal attacks that are attack like.<br>
    * E.g., normal accesses that read data really slowly (slow read),
    * normal accesses that refresh really quick (flood)
    * @return The path
    */
   public abstract String getNoiseNormalPath();
   
   public abstract String getNoiseSlowBodyPath();
   public abstract String getNoiseSlowHeadersPath();
   public abstract String getNoiseSlowReadPath();
   public abstract String getNoiseTCPFloodPath();
   public abstract String getNoiseUDPFloodPath();
   public abstract String getNoiseHTTPFloodPath();
   
   public abstract String getKDDTrainPath() throws UnsupportedOperationException;
}
