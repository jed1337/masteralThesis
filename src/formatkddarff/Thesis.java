package formatkddarff;

import com.sun.javafx.geom.Crossings;
import formatkddarff.classify.Classify;
import formatkddarff.classify.CrossValidation;
import formatkddarff.classify.TrainTest;
import formatkddarff.format.FormatAsText;
import formatkddarff.format.FormatAsArff;
import formatkddarff.utils.UtilsClssifiers;
import formatkddarff.utils.UtilsInstances;
import formatkddarff.utils.Utils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Thesis{
   private static HashMap<String, String> replaceAttribute(String attribute, String... toReplace){
    return replaceAttribute(attribute, String.join(",",toReplace));
   }

   private static HashMap<String, String> replaceAttribute(String attribute, String toReplace){
      final HashMap<String, String> OTHER_REPLACEMENTS = new HashMap<>();
      OTHER_REPLACEMENTS.put(
         "(?m)^@attribute "+attribute+".*",
         "@attribute "+attribute+" {"+toReplace+"}");

      return OTHER_REPLACEMENTS;
   }

   private static HashMap<String, String> getHashMap(String value, String... keys) {
      return getHashMap(value, Arrays.asList(keys));
   }

   private static HashMap<String, String> getHashMap(String value, List<String> keys) {
      HashMap<String, String> hm = new HashMap();
      keys.forEach((key)->{
         hm.put(key, value);
      });
      return hm;
   }

   private static void formatAsText(String fileName) throws IOException {
      FormatAsText fText = new FormatAsText(fileName);
      fText.replaceAllStrings(
//        replaceAttribute("service", SERVICES),
//        replaceAttribute("isAttack", "highrate", "lowrate", "normal")
        replaceAttribute("isAttack", "neptune", "lowrate", "normal")
//        replaceAttribute("isAttack", "slowBody","slowHeaders","slowRead","tcpFlood","udpFlood","httpFlood","normal")
        // replaceAttribute("isAttack", "tcpFlood","udpFlood","httpFlood","neptune","smurf")
//        replaceAttribute("isAttack", ALL_ATTACKS)
      );
   }

//   private static final String SERVICES = "aol,auth,bgp,courier,csnet_ns,ctf,daytime,discard,domain,domain_u,echo,eco_i,ecr_i,efs,exec,finger,ftp,ftp_data,gopher,harvest,hostnames,http,http_2784,http_443,http_8001,imap4,IRC,iso_tsap,klogin,kshell,ldap,link,login,mtp,name,netbios_dgm,netbios_ns,netbios_ssn,netstat,nnsp,nntp,ntp_u,other,pm_dump,pop_2,pop_3,printer,private,red_i,remote_job,rje,shell,smtp,sql_net,ssh,sunrpc,supdup,systat,telnet,tftp_u,tim_i,time,urh_i,urp_i,uucp,uucp_path,vmnet,whois,X11,Z39_50";
   private static final String ALL_ATTACKS = "apache2,back,buffer_overflow,ftp_write,guess_passwd,httptunnel,imap,ipsweep,land,loadmodule,mailbomb,mscan,multihop,named,neptune,nmap,normal,perl,phf,pod,portsweep,processtable,ps,rootkit,saint,satan,sendmail,smurf,snmpgetattack,snmpguess,spy,sqlattack,teardrop,udpstorm,warezclient,warezmaster,worm,xlock,xsnoop,xterm";
   private static final String[] FEATURES_TO_REMOVE = {"service","land","hot","num_failed_logins","logged_in","num_compromised","root_shell","su_attempted","num_root","num_file_creations","num_shells","num_access_files","num_outbound_cmds","is_host_login","is_guest_login","difficulty"};

   private static final String UNFORMATTED_DIR = "Data/RawFiles/";
   private static final String FORMATTED_DIR   = "Data/FormattedFiles/";

   private static final String CROSSFOLD_NAME = "Crossfold.arff";

   private static final String KDD_TEST             = "KDDTest+.arff";
   private static final String KDD_TEST_MINUS_21    = "KDDTest-21.arff";
   private static final String KDD_TRAIN            = "KDDTrain+.arff";
   private static final String KDD_TRAIN_20_PERCENT = "KDDTrain+_20Percent.arff";

   private static final String CNIS_HIGHRATE = "CNISHighrate.arff";
   private static final String CNIS_LOWRATE  = "CNISLowrate.arff";
   private static final String CNIS_NOISE  = "CNISNoise.arff";

   private static FormatAsText fat;

//   private static final String[] LOW_RATE_ATTACKS     = {"slowBody", "slowHeaders", "slowRead"};
//   private static final String[] HIGH_RATE_ATTACKS    = {"back", "land", "neptune", "pod", "smurf", "teardrop"};
//   private static final String[] HIGH_RATE_AND_NORMAL = {"normal","back", "land", "neptune", "pod", "smurf", "teardrop"};

   public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
      FormatAsArff formatNoise = new FormatAsArff(UNFORMATTED_DIR+ CNIS_NOISE);
      formatNoise.removeNonMatchingClasses("service", "http");
      formatNoise.removeAttributes(FEATURES_TO_REMOVE);
      formatNoise.randomise(11);
      formatNoise.keepXInstances("isAttack", "normal", 1500);
      formatNoise.setSaveFileFullPath(FORMATTED_DIR+ CNIS_NOISE);
      Utils.writeFile(
         formatNoise.getSaveFileFullPath(),
         formatNoise.getInstances().toString(),
         false);
      formatAsText(formatNoise.getSaveFileFullPath());

      fat = new FormatAsText(formatNoise.getSaveFileFullPath());
//      fat.replaceAllStrings(getHashMap("highrate", "tcpFlood","udpFlood","httpFlood"));
//
      FormatAsArff formatNormal = new FormatAsArff(UNFORMATTED_DIR+KDD_TRAIN);
//      formatNormal.removeNonMatchingClasses("isAttack", "normal", "neptune");
      formatNormal.removeNonMatchingClasses("isAttack", "normal", "neptune");
      formatNormal.removeNonMatchingClasses("service", "http");
      formatNormal.removeAttributes(FEATURES_TO_REMOVE); //Removes land; isRoot, etc features; difficulty
      formatNormal.randomise(11);
      formatNormal.keepXInstances("isAttack", "normal", 1500);
      formatNormal.keepXInstances("isAttack", "neptune", 1500);
      formatNormal.setSaveFileFullPath(FORMATTED_DIR+KDD_TRAIN);
      Utils.writeFile(
         formatNormal.getSaveFileFullPath(),
         formatNormal.getInstances().toString(),
         false);
      formatAsText(formatNormal.getSaveFileFullPath());
      fat = new FormatAsText(formatNormal.getSaveFileFullPath());
//
//      FormatAsArff formatHighRate = new FormatAsArff(UNFORMATTED_DIR + CNIS_HIGHRATE);
//      formatHighRate.removeNonMatchingClasses("service", "http");
//      formatHighRate.removeAttributes(FEATURES_TO_REMOVE); //Removes land; isRoot, etc features; difficulty
//      formatHighRate.randomise(11);
//      formatHighRate.keepXInstances("isAttack", "tcpFlood", 1000);
//      formatHighRate.keepXInstances("isAttack", "udpFlood", 1000);
//      formatHighRate.keepXInstances("isAttack", "httpFlood", 1000);
//      formatHighRate.setSaveFileFullPath(FORMATTED_DIR + CNIS_HIGHRATE);
//      Utils.writeFile(
//              formatHighRate.getSaveFileFullPath(),
//              formatHighRate.getInstances().toString(),
//              false);
//      formatAsText(formatHighRate.getSaveFileFullPath());
//      fat = new FormatAsText(formatHighRate.getSaveFileFullPath());
//      fat.replaceAllStrings(getHashMap("neptune", "tcpFlood","udpFlood","httpFlood"));
//      fat.replaceAllStrings(getHashMap("highrate", "tcpFlood","udpFlood","httpFlood"));

      FormatAsArff fLowrate = new FormatAsArff(UNFORMATTED_DIR + CNIS_LOWRATE);
      fLowrate.removeNonMatchingClasses("service", "http");
      fLowrate.removeAttributes(FEATURES_TO_REMOVE); //Removes land; isRoot, etc features; difficulty
      fLowrate.randomise(11);
      fLowrate.keepXInstances("isAttack", "slowBody", 1000);
      fLowrate.keepXInstances("isAttack", "slowHeaders", 1000);
      fLowrate.keepXInstances("isAttack", "slowRead", 1000);
      fLowrate.setSaveFileFullPath(FORMATTED_DIR + CNIS_LOWRATE);
      Utils.writeFile(
              fLowrate.getSaveFileFullPath(),
              fLowrate.getInstances().toString(),
              false);
      formatAsText(fLowrate.getSaveFileFullPath());
      fat = new FormatAsText(fLowrate.getSaveFileFullPath());
      fat.replaceAllStrings(getHashMap("lowrate","slowBody","slowHeaders","slowRead"));

      fat = new FormatAsText(FORMATTED_DIR+"Train.arff");
      fat.clearFile();
      fat.addInstances(formatNoise.getSaveFileFullPath(), "");
      fat.addInstances(formatNormal.getSaveFileFullPath(),"@data");
      fat.addInstances(fLowrate.getSaveFileFullPath(),"@data");
      fat.addClassCount("isAttack");

//      FormatAsArff formatTest = new FormatAsArff(UNFORMATTED_DIR+ KDD_TEST_MINUS_21);
//      formatTest.removeNonMatchingClasses("service", "http");
//      formatTest.removeNonMatchingClasses("isAttack", "normal", "neptune");
//      formatTest.removeAttributes(FEATURES_TO_REMOVE);
//      formatTest.randomise(11);
//      formatTest.keepXInstances("isAttack", "normal", 1500);
//      formatTest.setSaveFileFullPath(FORMATTED_DIR+ KDD_TEST_MINUS_21);
//      Utils.writeFile(
//         formatTest.getSaveFileFullPath(),
//         formatTest.getInstances().toString(),
//         false);
//      formatAsText(formatTest.getSaveFileFullPath());
//
//      fat = new FormatAsText(formatTest.getSaveFileFullPath());
//      fat.addClassCount("isAttack");

      Classify classify = new CrossValidation("allah/",
              FORMATTED_DIR+"Train.arff"
//              FORMATTED_DIR+KDD_TEST_MINUS_21
      );
      classify.buildModel();
      classify.evaluateModel();
   }
}
