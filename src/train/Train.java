package train;

import constants.FileNameConstants;
import constants.PathConstants;
import format.FormatAsArff;
import format.FormatAsText;
import utils.Utils;
import java.io.IOException;
import java.util.HashMap;

public abstract class Train {
   private final String[] FEATURES_TO_REMOVE = {"service","land","hot","num_failed_logins","logged_in","num_compromised","root_shell","su_attempted","num_root","num_file_creations","num_shells","num_access_files","num_outbound_cmds","is_host_login","is_guest_login","difficulty"};
   private final int RANDOM_SEED = 11;

   protected final FormatAsArff faa;
   protected final FormatAsText fat;

   protected Train(FileNameConstants fileName) throws IOException{
      faa = new FormatAsArff (PathConstants.UNFORMATTED_DIR+""+fileName);
      faa.setSaveFileFullPath(PathConstants.FORMATTED_DIR+  ""+fileName);
//      ...
      fat = new FormatAsText(faa.getSaveFileFullPath());
   }

   public void setup() throws IOException, Exception{
      removeNonMatchingClasses();

      this.faa.removeAttributes(this.FEATURES_TO_REMOVE);
      this.faa.randomise(this.RANDOM_SEED);

      keepXInstances();
   }

   protected abstract void removeNonMatchingClasses();
   protected abstract void keepXInstances();
   
   public void replaceAllStrings(HashMap<String, String>... hashMaps) throws IOException {
      fat.replaceAllStrings(hashMaps);
   }

   public void writeFile() throws IOException{
      Utils.writeFile(
        faa.getSaveFileFullPath(),
        faa.getInstances().toString(),
        false);
   }

   public FormatAsArff getFaa() {
      return faa;
   }

   public FormatAsText getFat() {
      return fat;
   }
   
}