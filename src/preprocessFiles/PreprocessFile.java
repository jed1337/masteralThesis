package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.AttributeTypeConstants;
import constants.FormatConstants;
import constants.DirectoryConstants;
import constants.SpecificAttackTypeEnum;
import featureExtraction.FeatureExtractionPaths;
import preprocessFiles.preprocessAs.FormatAsArff;
import java.io.IOException;
import java.util.function.Function;

/**
 * A class That setups the files to be used by the classifier<p>
 * This class doesn't classify<p>
 * This class keeps X instances, and certain attack types as stated by its sub classes<p>
 * This is sort of like the Template method wherein the subclasses supply the parameters
 */
public final class PreprocessFile {
   private final int RANDOM_SEED = 11;

   private final Enum<GeneralAttackTypeEnum> generalAttackType;
   private final Enum<SpecificAttackTypeEnum> specificAttackType;
   private final FormatAsArff faa;

   private PreprocessFile(PreprocessFile.PreprocessFileBuilder builder) throws IOException, Exception{
      this.generalAttackType = builder.generalAttackType;
      this.specificAttackType = builder.specificAttackType;
      String filename = builder.filenameFunction.apply(builder.fExtractionPath);
      
      this.faa = new FormatAsArff (DirectoryConstants.UNFORMATTED_DIR+""+filename);
      this.faa.setSavePath(DirectoryConstants.FORMATTED_DIR+  ""+filename);
      
      this.removeNonMatchingClasses();
      this.others();

      if (builder.instancesCount != -1) {
         this.balanceInstances(builder.instancesCount);
      }
      if(!builder.relabel.isEmpty()){
         this.relabel(
            AttributeTypeConstants.ATTRIBUTE_CLASS,
            builder.relabel
         );
      }
   }

//   protected PreprocessFile(String fileName, Enum<GeneralAttackType> generalAttackType, String specificAttackTypes)
//           throws IOException {
//      this.generalAttackType = generalAttackType;
//      this.specificAttackType = specificAttackTypes;
//
//      this.faa = new FormatAsArff (DirectoryConstants.UNFORMATTED_DIR+""+fileName);
//      this.faa.setSavePath(DirectoryConstants.FORMATTED_DIR+  ""+fileName);
//   }

   public Enum<GeneralAttackTypeEnum> getGeneralAttackType() {
      return generalAttackType;
   }

   public Enum<SpecificAttackTypeEnum> getSpecificAttackType() {
      return specificAttackType;
   }

   public FormatAsArff getFaa() {
      return faa;
   }

   private void others() throws IOException, Exception{
      this.faa.removeAttributes(FormatConstants.FEATURES_TO_REMOVE);
      this.faa.randomise(this.RANDOM_SEED);
   }

   private void removeNonMatchingClasses() {
      this.faa.removeNonMatchingClasses(AttributeTypeConstants.ATTRIBUTE_CLASS, this.specificAttackType.name());
      this.faa.removeNonMatchingClasses("service", "http", "http_443");
   }

   /**
    * For example there are 5000 instances
    * <p>
    * {@Literal @}attributeName{val1, val2}
    * <p>
    * Output: 2500 instances val1, 2500 instances val2
    * <p>
    * If setInstanceCount(int) isn't called beforehand, this function doesn't do anything
    */
   private void balanceInstances(int instanceCount) {
      //Todo, make not directly the last index
      int lastIndex = this.faa.getInstances().numAttributes()-1;
      this.faa.keepXInstances(lastIndex, this.specificAttackType.toString(), instanceCount);
   }
   
   private void relabel(String attributes, String toReplace) throws Exception {
      this.faa.renameNominalValues(attributes, toReplace);
   }

   public static class PreprocessFileBuilder{
      private final Function<FeatureExtractionPaths, String> filenameFunction;
      private final Enum<GeneralAttackTypeEnum> generalAttackType;
      private final Enum<SpecificAttackTypeEnum> specificAttackType;
      private int instancesCount=-1;
      private String relabel="";
      private FeatureExtractionPaths fExtractionPath;

      public PreprocessFileBuilder(Function<FeatureExtractionPaths, String> filenameFunction, Enum<GeneralAttackTypeEnum> generalAttackType, Enum<SpecificAttackTypeEnum> specificAttackType) {
         this.filenameFunction = filenameFunction;
         this.generalAttackType = generalAttackType;
         this.specificAttackType = specificAttackType;
      }
      
      public PreprocessFileBuilder instancesCount(int instancesCount) {
         this.instancesCount = instancesCount;
         return this;
      }
      
      public PreprocessFileBuilder relabel(String relabel){
         this.relabel = relabel;
         return this;
      }
      
      public PreprocessFile build(FeatureExtractionPaths fExtractionPath) throws IOException, Exception{
         this.fExtractionPath = fExtractionPath;
         return new PreprocessFile(this);
      }
   }
}