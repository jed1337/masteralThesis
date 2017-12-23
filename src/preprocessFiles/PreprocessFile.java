package preprocessFiles;

import constants.AttributeTypeConstants;
import constants.FormatConstants;
import constants.DirectoryConstants;
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
   private final Enum<GeneralAttackType> generalAttackType;
   private final String[] attackTypes;
   private final FormatAsArff faa;

   private int instancesCount = -1;
   
   protected PreprocessFile(String fileName, Enum<GeneralAttackType> generalAttackType, String[] specificAttackTypes)
           throws IOException {
      this.generalAttackType = generalAttackType;
      this.attackTypes = specificAttackTypes;

      this.faa = new FormatAsArff (DirectoryConstants.UNFORMATTED_DIR+""+fileName);
      this.faa.setSavePath(DirectoryConstants.FORMATTED_DIR+  ""+fileName);
   }

   public final void setUp() throws IOException, Exception{
      removeNonMatchingClasses();

      this.faa.removeAttributes(FormatConstants.FEATURES_TO_REMOVE);
      this.faa.randomise(this.RANDOM_SEED);

      balanceInstances();
   }

   public final void rename(String attributes, String toReplace) throws Exception {
      this.faa.renameNominalValues(attributes, toReplace);
      System.out.println("");
   }

   public final void setInstancesCount(int instancesCount) {
      this.instancesCount = instancesCount;
   }

   public final Enum<GeneralAttackType> getGeneralAttackType() {
      return generalAttackType;
   }
   
   public final FormatAsArff getFaa() {
      return faa;
   }

   private void removeNonMatchingClasses() {
      this.faa.removeNonMatchingClasses(AttributeTypeConstants.ATTRIBUTE_CLASS, this.attackTypes);
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
   private void balanceInstances() {
      if(instancesCount == -1){
         return;
      }
      
      int lastIndex = this.faa.getInstances().numAttributes()-1;
      for (String attackType : this.attackTypes) {
         this.faa.keepXInstances(lastIndex, attackType, getDivider());
      }
   }

   private int getDivider(){
      return Math.round(this.instancesCount/this.attackTypes.length);
   }
}