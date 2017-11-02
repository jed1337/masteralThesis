package formatkddarff;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.core.Instances;

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
   
   private static HashMap<String, String> getHashMap(String[] keys, String value) {
      return getHashMap(Arrays.asList(keys), value);
   }
   
   private static HashMap<String, String> getHashMap(List<String> keys, String value) {
      HashMap<String, String> hm = new HashMap();
      keys.forEach((key)->{
         hm.put(key, value);
      });
      return hm;
   }

   private static void formatText(String fileName) throws IOException {
      FormatAsText fText = new FormatAsText(fileName);
      fText.replaceAllStrings(
        replaceAttribute("isAttack", "normal", "neptune", "tcpFlood"),
        replaceAttribute("service", SERVICES)
      );
   }

   private static final String SERVICES = "aol,auth,bgp,courier,csnet_ns,ctf,daytime,discard,domain,domain_u,echo,eco_i,ecr_i,efs,exec,finger,ftp,ftp_data,gopher,harvest,hostnames,http,http_2784,http_443,http_8001,imap4,IRC,iso_tsap,klogin,kshell,ldap,link,login,mtp,name,netbios_dgm,netbios_ns,netbios_ssn,netstat,nnsp,nntp,ntp_u,other,pm_dump,pop_2,pop_3,printer,private,red_i,remote_job,rje,shell,smtp,sql_net,ssh,sunrpc,supdup,systat,telnet,tftp_u,tim_i,time,urh_i,urp_i,uucp,uucp_path,vmnet,whois,X11,Z39_50";
   private static final String ALL_ATTACKS = "apache2,back,buffer_overflow,ftp_write,guess_passwd,httptunnel,imap,ipsweep,land,loadmodule,mailbomb,mscan,multihop,named,neptune,nmap,normal,perl,phf,pod,portsweep,processtable,ps,rootkit,saint,satan,sendmail,smurf,snmpgetattack,snmpguess,spy,sqlattack,teardrop,udpstorm,warezclient,warezmaster,worm,xlock,xsnoop,xterm";
   private static final String[] FEATURES_TO_REMOVE = {"land","hot","num_failed_logins","logged_in","num_compromised","root_shell","su_attempted","num_root","num_file_creations","num_shells","num_access_files","num_outbound_cmds","is_host_login","is_guest_login","difficulty"};
   
   public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
      final String UNFORMATTED_DIR = "Data/RawFiles/";
      final String FORMATTED_DIR   = "Data/FormattedFiles/";

      final String KDD_TEST             = "KDDTest+.arff";
      final String KDD_TEST_MINUS_21    = "KDDTest-21.arff";
      final String KDD_TRAIN            = "KDDTrain+.arff";
      final String KDD_TRAIN_20_Percent = "KDDTrain+_20Percent.arff";
      final String CNIS_HIGHRATE        = "CNISHighrate.arff";

      final String[] LOW_RATE_ATTACKS     = {"slowBody", "slowHeaders", "slowRead"};
		final String[] HIGH_RATE_ATTACKS    = {"back", "land", "neptune", "pod", "smurf", "teardrop"};
		final String[] HIGH_RATE_AND_NORMAL = {"normal","back", "land", "neptune", "pod", "smurf", "teardrop"};
      
      FormatAsArff fArff = new FormatAsArff(UNFORMATTED_DIR+KDD_TRAIN_20_Percent);
      fArff.removeAttributes(FEATURES_TO_REMOVE); //Removes land; isRoot, etc features; difficulty
      fArff.randomise(11);
      fArff.removeNonMatchingClasses("isAttack", "normal", "neptune");
      fArff.keepXInstances("isAttack", "normal", 5000);
      fArff.keepXInstances("isAttack", "neptune", 5000);
      fArff.setSaveFileName(FORMATTED_DIR+KDD_TRAIN_20_Percent);
      Utils.writeFile(
         fArff.getSaveFileName(), 
         fArff.getInstances().toString(), 
         false);
      formatText(fArff.getSaveFileName());

      fArff = new FormatAsArff(UNFORMATTED_DIR+ CNIS_HIGHRATE);
      fArff.removeAttributes(FEATURES_TO_REMOVE); 
      fArff.randomise(11);
      fArff.keepXInstances("isAttack", "tcpFlood", 5000);
      fArff.setSaveFileName(FORMATTED_DIR+CNIS_HIGHRATE);
      Utils.writeFile(
         fArff.getSaveFileName(), 
         fArff.getInstances().toString(), 
         false);
      formatText(fArff.getSaveFileName());

      
//      fText = new FormatAsText(FORMATTED_DIR+"Crossfold.arff");
//      fText.clearFile();
//      fText.addInstances(FORMATTED_DIR+CNIS_HIGHRATE,"");
//      fText.addInstances(FORMATTED_DIR+KDD_TRAIN_20_Percent, "@data");

//build model
//      final String folderPath = "results/NormalLowrateLoic/";
//      
//      Instances trainingSet = UtilsInstances.getInstances(FORMATTED_DIR+"Crossfold.arff");
//      trainingSet.setClassIndex(trainingSet.numAttributes()-1);
//
////      Instances testSet = Utils.getInstances(FORMATTED_KDD_TEST_MINUS_21);
////      testSet.setClassIndex(testSet.numAttributes()-1);
//
//      ArrayList<ClassifierHolder> classifiers = new ArrayList<>();
//      classifiers.add(new ClassifierHolder(new NaiveBayes(), trainingSet, "NB", folderPath));
//      classifiers.add(new ClassifierHolder(new IBk(), trainingSet, "KNN", folderPath));
//      classifiers.add(new ClassifierHolder(new J48(), trainingSet, "J48", folderPath));
//      classifiers.add(new ClassifierHolder(new SMO(), trainingSet, "SMO", folderPath));
////      classifiers.add(new ClassifierHolder(new MultilayerPerceptron(), instances, "MLP", "models/3Classes"));
//
//      for (ClassifierHolder ch : classifiers) {
//         UtilsClssifiers.writeModel(ch);
////         UtilsClssifiers.saveTestEvaluationToFile(ch, testSet);
//         UtilsClssifiers.saveCrossValidationToFile(ch, 10);
////         UtilsClssifiers.saveCrossValidationToFile(
////                 UtilsClssifiers.readModel(ch.getModelName()),
////                 instances,
////                 ch.getClassifierName(),
////                 ch.getResultName());
//      }
   }
}
