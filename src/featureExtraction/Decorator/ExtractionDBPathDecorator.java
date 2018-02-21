package featureExtraction.Decorator;

import featureExtraction.FeatureExtraction;
import java.util.List;
import java.util.function.Consumer;
import preprocessFiles.preprocessAs.FormatAsArff;

/**
 * A decorator class whose parameters are provided by its subclasses.<br>
 * It was created in order to not create a lot of classes depending on which
 * dataset was used.
 */
public abstract class ExtractionDBPathDecorator extends FeatureExtraction{
   private final FeatureExtraction fe;
   private final String path;

   /**
    * Is protected since the subclass assigns the path
    * @param fe
    * @param path
    */
   protected ExtractionDBPathDecorator(FeatureExtraction fe, String path) {
      this.fe = fe;
      this.path = path;
   }

   /**
    * Doesn't decorate this function. <br>
    * Simply calls the function of its FeatureExtraction object
    * @return return this.fe.getName();
    */
   @Override
   public String getName() {
      return this.fe.getName();
   }

   @Override
   public String getNormalPath() {
      return this.path+this.fe.getNormalPath();
   }

   @Override
   public String getSlowBodyPath() {
      return this.path+this.fe.getSlowBodyPath();
   }

   @Override
   public String getSlowHeadersPath() {
      return this.path+this.fe.getSlowHeadersPath();
   }

   @Override
   public String getSlowReadPath() {
      return this.path+this.fe.getSlowReadPath();
   }

   @Override
   public String getTCPFloodPath() {
      return this.path+this.fe.getTCPFloodPath();
   }

   @Override
   public String getUDPFloodPath() {
      return this.path+this.fe.getUDPFloodPath();
   }

   @Override
   public String getHTTPFloodPath() {
      return this.path+this.fe.getHTTPFloodPath();
   }

   @Override
   public String getNoiseSlowBodyPath() {
      return this.path+this.fe.getNoiseSlowBodyPath();
   }

   @Override
   public String getNoiseSlowHeadersPath() {
      return this.path+this.fe.getNoiseSlowHeadersPath();
   }

   @Override
   public String getNoiseSlowReadPath() {
      return this.path+this.fe.getNoiseSlowReadPath();
   }

   @Override
   public String getNoiseTCPFloodPath() {
      return this.path+this.fe.getNoiseTCPFloodPath();
   }

   @Override
   public String getNoiseUDPFloodPath() {
      return this.path+this.fe.getNoiseUDPFloodPath();
   }

   @Override
   public String getNoiseHTTPFloodPath() {
      return this.path+this.fe.getNoiseHTTPFloodPath();
   }

   @Override
   public String getKDDTrainPath() throws UnsupportedOperationException {
      return this.path+this.fe.getKDDTrainPath();
   }

   /**
    * Doesn't decorate this function. <br>
    * Simply calls the function of its FeatureExtraction object
    * @return this.fe.getFeaturesToRemove();
    */
   @Override
   public List<String> getFeaturesToRemove() {
      return this.fe.getFeaturesToRemove();
   }

   /**
    * Doesn't decorate this function. <br>
    * Simply calls the function of its FeatureExtraction object
    * @return this.fe.removeNonMatchingClasses();
    */
   @Override
   public Consumer<FormatAsArff> removeNonMatchingClasses() {
      return this.fe.removeNonMatchingClasses();
   }

   /**
    * this.path is set by the protected constructor
    * @return this.path
    */
   @Override
   public String getDatasetName() {
      return this.path;
   }
}