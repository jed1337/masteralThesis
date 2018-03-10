package preprocessFiles;

<<<<<<< HEAD
=======
import constants.GeneralAttackTypeEnum;
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
import constants.AttributeTypeConstants;
import constants.DirectoryConstants;
<<<<<<< HEAD
import constants.GeneralAttackTypeEnum;
import constants.SpecificAttackTypeEnum;
import globalParameters.GlobalFeatureExtraction;
=======
import constants.SpecificAttackTypeEnum;
import preprocessFiles.preprocessAs.FormatAsArff;
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
import java.io.IOException;
import preprocessFiles.preprocessAs.FormatAsArff;

/**
 * A class That setups the files to be used by the classifier<p>
 * This class doesn't classify<p>
 * This class keeps X instances, and certain attack types as stated by its sub classes<p>
 * This is sort of like the Template method wherein the subclasses supply the parameters
 */
public abstract class PreprocessFile {
   private final int RANDOM_SEED = 11;
<<<<<<< HEAD
=======

>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
   private final GeneralAttackTypeEnum  generalAttackType;
   private final SpecificAttackTypeEnum specificAttackType;
   private final FormatAsArff faa;

   private int instancesCount = -1;
<<<<<<< HEAD

   protected PreprocessFile(String filename, GeneralAttackTypeEnum generalAttackType, SpecificAttackTypeEnum specificAttackType)
           throws IOException {
      this.generalAttackType = generalAttackType;
      this.specificAttackType = specificAttackType;

      this.faa = new FormatAsArff(DirectoryConstants.UNFORMATTED_DIR+filename);
      this.faa.setSavePath(DirectoryConstants.FORMATTED_DIR+filename);
=======
      
   protected PreprocessFile(String fileName, GeneralAttackTypeEnum generalAttackType, SpecificAttackTypeEnum specificAttackTypes)
           throws IOException {
      this.generalAttackType = generalAttackType;
      this.specificAttackType = specificAttackTypes;
      
      this.faa = new FormatAsArff (DirectoryConstants.UNFORMATTED_DIR+""+fileName);
      this.faa.setSavePath(DirectoryConstants.FORMATTED_DIR+  ""+fileName);
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
   }

   public final void setUp() throws IOException, Exception{
      removeNonMatchingClasses();

      this.faa.removeAttributes(
            GlobalFeatureExtraction.getInstance().getFeaturesToRemove()
      );
      
      this.faa.randomise(this.RANDOM_SEED);

<<<<<<< HEAD
      keepXInstances();
   }
=======
      balanceInstances();
      }
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9

   /**
    * Uses the renameNominalValuesfunction in this.faa
    * @see preprocessFiles.preprocessAs.FormatAsArff#renameNominalValues(String, String)
    * 
    * @param attributes
    * @param toReplace
    * @throws java.lang.Exception
    */
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

   public int getInstancesCount() {
      return instancesCount;
   }

   /**
    * Uses the removeNonMatchingClasses in this.faa
    * @see preprocessFiles.preprocessAs.FormatAsArff#removeNonMatchingClasses(String, String...)
    */
   private void removeNonMatchingClasses() {
      this.faa.removeNonMatchingClasses(AttributeTypeConstants.ATTRIBUTE_CLASS, this.specificAttackType.getValue());
<<<<<<< HEAD
      GlobalFeatureExtraction.getInstance()
         .removeNonMatchingClasses().accept(this.faa);
=======
      this.faa.removeNonMatchingClasses("service", "http", "http_443");
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
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
   private void keepXInstances() {
      if(this.instancesCount == -1){
         return; //Exit this function (Don't do any balancing)
      }

      //Todo, make not directly the last index
      int lastIndex = this.faa.getInstances().numAttributes()-1;
      this.faa.keepXInstances(lastIndex, this.specificAttackType.getValue(), this.instancesCount);
   }
}
