package preprocessFiles;

import constants.AttributeTypeConstants;
import constants.FormatConstants;
import constants.PathConstants;
import preprocessFiles.preprocessAs.FormatAsArff;
import java.io.IOException;

/**
 * An abstract class That setups the files to be used by the classifier<p>
 * This class doesn't classify<p>
 * This class keeps X instances, and certain attack types as stated by its sub classes<p>
 * This is sort of like the Template method wherein the subclasses supply the parameters
 */
public abstract class PreprocessFile {
//   private final String[] FEATURES_TO_REMOVE = {"service","land","hot","num_failed_logins","logged_in","num_compromised","root_shell","su_attempted","num_root","num_file_creations","num_shells","num_access_files","num_outbound_cmds","is_host_login","is_guest_login","difficulty"};
   private final int RANDOM_SEED = 11;
   private final Enum<AttackType> specificAttackType;
   private final String[] attackTypes;
   private final FormatAsArff faa;

   private int instancesCount = -1;
   
   protected PreprocessFile(String fileName, Enum<AttackType> specificAttackType, String[] attackTypes) throws IOException{
      this.specificAttackType = specificAttackType;
      this.attackTypes = attackTypes;

      this.faa = new FormatAsArff (PathConstants.UNFORMATTED_DIR+""+fileName);
      this.faa.setSavePath(PathConstants.FORMATTED_DIR+  ""+fileName);
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

   public Enum<AttackType> getSpecificAttackType() {
      return specificAttackType;
   }
   
   public final FormatAsArff getFaa() {
      return faa;
   }

   private void removeNonMatchingClasses() {
      this.faa.removeNonMatchingClasses(AttributeTypeConstants.ATTRIBUTE_CLASS, this.attackTypes);
      this.faa.removeNonMatchingClasses("service", "http", "http_443");
   }
   
   /**
    * For example there are 10000 instances
    * <p>
    * {@ Literal @} attributeName{val1, val2}
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