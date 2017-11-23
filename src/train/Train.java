package train;

import constants.FormatConstants;
import constants.PathConstants;
import format.FormatAsArff;
import format.FormatAsText;
import utils.Utils;
import java.io.IOException;
import java.util.HashMap;

public abstract class Train {
//   private final String[] FEATURES_TO_REMOVE = {"service","land","hot","num_failed_logins","logged_in","num_compromised","root_shell","su_attempted","num_root","num_file_creations","num_shells","num_access_files","num_outbound_cmds","is_host_login","is_guest_login","difficulty"};
   private final int RANDOM_SEED = 11;

   protected final FormatAsArff faa;
   protected final FormatAsText fat;
   
   protected final int instancesCount;
   
   private final String[] attackTypes;
   
   protected Train(int instancesCount, String fileName, String[] attackTypes) throws IOException{
      this.instancesCount = instancesCount;
      this.attackTypes = attackTypes;
      
      faa = new FormatAsArff (PathConstants.UNFORMATTED_DIR+""+fileName);
      faa.setSavePath(PathConstants.FORMATTED_DIR+  ""+fileName);
      fat = new FormatAsText(faa.getSavePath());
   }

   public final void setup() throws IOException, Exception{
      removeNonMatchingClasses();

      this.faa.removeAttributes(FormatConstants.FEATURES_TO_REMOVE);
      this.faa.randomise(this.RANDOM_SEED);

      keepXInstances();
   }

   public void replaceAllStrings(HashMap<String, String>... hashMaps) throws IOException {
      fat.replaceAllStrings(hashMaps);
   }

   public void writeFile() throws IOException{
      Utils.writeStringFile(
        faa.getSavePath(),
        faa.getInstances().toString()
      );
   }

   public FormatAsArff getFaa() {
      return faa;
   }

   public FormatAsText getFat() {
      return fat;
   }
   
   private void removeNonMatchingClasses() {
      this.faa.removeNonMatchingClasses("isAttack", this.attackTypes);
      this.faa.removeNonMatchingClasses("service", "http", "http_443");
//      this.faa.removeNonMatchingClasses("service", "http");
   }
   
   private void keepXInstances() {
      for (String attackType : this.attackTypes) {
         this.faa.keepXInstances("isAttack", attackType, getDivider());
      }
   }
   
   private int getDivider(){
      return Math.round(this.instancesCount/this.attackTypes.length);
   }
}