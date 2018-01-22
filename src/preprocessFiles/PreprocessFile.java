package preprocessFiles;

import constants.AttributeTypeConstants;
import constants.GeneralAttackTypeEnum;
import constants.DirectoryConstants;
import constants.SpecificAttackTypeEnum;
import globalParameters.GlobalFeatureExtraction;
import preprocessFiles.preprocessAs.FormatAsArff;
import java.io.IOException;

/**
 * An abstract class That setups the files to be used by the classifier<p>
 * This class doesn't classify<p>
 * This class keeps X instances, and certain attack types as stated by its sub classes<p>
 * This is sort of like the Template method wherein the subclasses supply the parameters
 */
public abstract class PreprocessFile {
   private final int RANDOM_SEED = 11;
   private final GeneralAttackTypeEnum  generalAttackType;
   private final SpecificAttackTypeEnum specificAttackType;
   private final FormatAsArff faa;

   private int instancesCount = -1;

   protected PreprocessFile(String filename, GeneralAttackTypeEnum generalAttackType, SpecificAttackTypeEnum specificAttackType)
           throws IOException {
      this.generalAttackType = generalAttackType;
      this.specificAttackType = specificAttackType;

      this.faa = new FormatAsArff(DirectoryConstants.UNFORMATTED_DIR+filename);
      this.faa.setSavePath(DirectoryConstants.FORMATTED_DIR+filename);
   }

   public final void setUp() throws IOException, Exception{
      removeNonMatchingClasses();

      this.faa.removeAttributes(
         GlobalFeatureExtraction.getInstance().getFeaturesToRemove()
      );
      this.faa.randomise(this.RANDOM_SEED);

      balanceInstances();
   }

   public final void relabel(String attributes, String toReplace) throws Exception {
      this.faa.renameNominalValues(attributes, toReplace);
   }

   public final void setInstancesCount(int instancesCount) {
      this.instancesCount = instancesCount;
   }

   public final GeneralAttackTypeEnum getGeneralAttackType() {
      return generalAttackType;
   }

   public final SpecificAttackTypeEnum getSpecificAttackType() {
      return specificAttackType;
   }

   public final FormatAsArff getFaa() {
      return faa;
   }

   private void removeNonMatchingClasses() {
      this.faa.removeNonMatchingClasses(AttributeTypeConstants.ATTRIBUTE_CLASS, this.specificAttackType.getValue());
      GlobalFeatureExtraction.getInstance()
         .removeNonMatchingClasses().accept(this.faa);
   }

   /**
    * For example there are 5000 instances
    * <br>
    * {@Literal @}attributeName{val1, val2}
    * <br>
    * Output: 2500 instances val1, 2500 instances val2
    * <br>
    * If setInstanceCount(int) isn't called beforehand, this function doesn't do anything
    */
   private void balanceInstances() {
      if(this.instancesCount == -1){
         return; //Exit this function (Don't do any balancing)
      }

      //Todo, make not directly the last index
      int lastIndex = this.faa.getInstances().numAttributes()-1;
      this.faa.keepXInstances(lastIndex, this.specificAttackType.getValue(), this.instancesCount);
   }
}