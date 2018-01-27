package featureExtraction;

public final class InitialDatabase extends ExtractionDBPathDecorator{
   /**
    * The path is statically passed to the super constructor. <br>
    * @param fe 
    */
   public InitialDatabase(FeatureExtraction fe) {
      super(
         fe, 
         "Initial/"
      );
   }
}