package formatkddarff;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public class Thesis{
   public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
      final String UNFORMATTED_DIRECTORY = "NSLKDDRawFiles/";
      final String FORMATTED_DIRECTORY   = "NSLKDDFormattedFiles/";

      final String UNFORMATTED_KDD_TEST             = UNFORMATTED_DIRECTORY+"UnformattedKDDTest+.arff";
      final String UNFORMATTED_KDD_TEST_MINUS_21    = UNFORMATTED_DIRECTORY+"UnformattedKDDTest-21.arff";
      final String UNFORMATTED_KDD_TRAIN            = UNFORMATTED_DIRECTORY+"UnformattedKDDTrain+.arff";
      final String UNFORMATTED_KDD_TRAIN_20_Percent = UNFORMATTED_DIRECTORY+"UnformattedKDDTrain+_20Percent.arff";

      final String FORMATTED_KDD_TEST               = FORMATTED_DIRECTORY+"FormattedKDDTest+.arff";
      final String FORMATTED_KDD_TEST_MINUS_21      = FORMATTED_DIRECTORY+"FormattedKDDTest-21.arff";
      final String FORMATTED_KDD_TRAIN              = FORMATTED_DIRECTORY+"FormattedKDDTrain+.arff";
      final String FORMATTED_KDD_TRAIN_20_Percent   = FORMATTED_DIRECTORY+"FormattedKDDTrain+_20Percent.arff";

      final String BALANCED_KDD_TRAIN               = FORMATTED_DIRECTORY+"BalancedKDDTrain.arff";
      final String BALANCED_KDD_TEST                = FORMATTED_DIRECTORY+"BalancedKDDTest.arff";

		final String CNIS_INPUT_FILENAME  = "CNIS input.arff";
		final String CNIS_OUTPUT_FILENAME = "CNIS output.arff";

      final ArrayList<String> LOW_RATE_ATTACKS     = getArrayList("slowBody", "slowHeaders", "slowRead");
//		final ArrayList<String> HIGH_RATE_ATTACKS    = getArrayList("back", "land", "neptune", "pod", "smurf", "teardrop");
		final ArrayList<String> HIGH_RATE_ATTACKS    = getArrayList("neptune");
		final ArrayList<String> HIGH_RATE_AND_NORMAL = (ArrayList<String>)HIGH_RATE_ATTACKS.clone();
      HIGH_RATE_AND_NORMAL.add("normal");

      final String SERVICES = "aol,auth,bgp,courier,csnet_ns,ctf,daytime,discard,domain,domain_u,echo,eco_i,ecr_i,efs,exec,finger,ftp,ftp_data,gopher,harvest,hostnames,http,http_2784,http_443,http_8001,imap4,IRC,iso_tsap,klogin,kshell,ldap,link,login,mtp,name,netbios_dgm,netbios_ns,netbios_ssn,netstat,nnsp,nntp,ntp_u,other,pm_dump,pop_2,pop_3,printer,private,red_i,remote_job,rje,shell,smtp,sql_net,ssh,sunrpc,supdup,systat,telnet,tftp_u,tim_i,time,urh_i,urp_i,uucp,uucp_path,vmnet,whois,X11,Z39_50";
      final String ALL_ATTACKS = "apache2,back,buffer_overflow,ftp_write,guess_passwd,httptunnel,imap,ipsweep,land,loadmodule,mailbomb,mscan,multihop,named,neptune,nmap,normal,perl,phf,pod,portsweep,processtable,ps,rootkit,saint,satan,sendmail,smurf,snmpgetattack,snmpguess,spy,sqlattack,teardrop,udpstorm,warezclient,warezmaster,worm,xlock,xsnoop,xterm";

      FormatAsArff faaTrain = new FormatAsArff(UNFORMATTED_KDD_TRAIN_20_Percent);
      faaTrain.removeAttributes("7,10-22,last"); //Removes land; isRoot, etc features; difficulty
      faaTrain.removeNonMatchingClasses("isAttack", HIGH_RATE_AND_NORMAL);
      faaTrain.writeArffInstances(FORMATTED_KDD_TRAIN_20_Percent);

      // FormatAsArff faaBalancedTrain = new FormatAsArff(faaTrain.getSaveFileName());
      // faaBalancedTrain.randomise(11);
      // faaBalancedTrain.removeNonMatchingClasses("isAttack", "neptune", "smurf", "normal");
      // faaBalancedTrain.keepXInstances("isAttack", "normal", 10);
      // faaBalancedTrain.keepXInstances("isAttack", "neptune", 10);
      // faaBalancedTrain.writeArffInstances(BALANCED_KDD_TRAIN);

      FormatAsText fatTrain = new FormatAsText(faaTrain.getSaveFileName());
      fatTrain.replaceAllStrings(
        replaceAttribute("isAttack", HIGH_RATE_AND_NORMAL),
        replaceAttribute("service", SERVICES)
//        replaceAttribute("isAttack", ALL_ATTACKS)
      );

      FormatAsArff faaTest = new FormatAsArff(UNFORMATTED_KDD_TEST_MINUS_21);
      faaTest.removeAttributes("7,10-22,last"); //Removes land; isRoot, etc features; difficulty
      faaTest.removeNonMatchingClasses("isAttack", HIGH_RATE_AND_NORMAL);
      faaTest.writeArffInstances(FORMATTED_KDD_TEST_MINUS_21);

      // FormatAsArff faaBalancedTest = new FormatAsArff(faaTest.getSaveFileName());
      // faaBalancedTest.randomise(11);
      // faaBalancedTest.removeNonMatchingClasses("isAttack", "neptune", "smurf", "normal");
      // faaBalancedTest.keepXInstances("isAttack", "normal", 90);
      // faaBalancedTest.keepXInstances("isAttack", "neptune", 90);
      // faaBalancedTest.writeArffInstances(BALANCED_KDD_TEST);

      FormatAsText fatTest = new FormatAsText(faaTest.getSaveFileName());
      fatTest.replaceAllStrings(
        replaceAttribute("isAttack", HIGH_RATE_AND_NORMAL),
        replaceAttribute("service", SERVICES)
//        replaceAttribute("isAttack", ALL_ATTACKS)
      );

      //build model
      final String folderPath = "results/OnlyDoSandNormal/";
      
      Instances trainingSet = Utils.getInstances(FORMATTED_KDD_TRAIN_20_Percent);
      trainingSet.setClassIndex(trainingSet.numAttributes()-1);

      Instances testSet = Utils.getInstances(FORMATTED_KDD_TEST_MINUS_21);
      testSet.setClassIndex(testSet.numAttributes()-1);

      ArrayList<ClassifierHolder> classifiers = new ArrayList<>();
      classifiers.add(new ClassifierHolder(new NaiveBayes(), trainingSet, "NB", folderPath));
      classifiers.add(new ClassifierHolder(new IBk(), trainingSet, "KNN", folderPath));
      classifiers.add(new ClassifierHolder(new J48(), trainingSet, "J48", folderPath));
      classifiers.add(new ClassifierHolder(new SMO(), trainingSet, "SMO", folderPath));
//      classifiers.add(new ClassifierHolder(new MultilayerPerceptron(), instances, "MLP", "models/3Classes"));

      for (ClassifierHolder ch : classifiers) {
//         UtilsClssifiers.writeModel(ch);
         UtilsClssifiers.saveTestEvaluationToFile(ch, testSet);
//         UtilsClssifiers.saveCrossValidationToFile(ch);
//         UtilsClssifiers.saveCrossValidationToFile(
//                 UtilsClssifiers.readModel(ch.getModelName()),
//                 instances,
//                 ch.getClassifierName(),
//                 ch.getResultName());
      }
   }
   private static HashMap<String, String> replaceAttribute(String attribute, ArrayList<String> toReplace){
      return replaceAttribute(attribute, getArrayListString(toReplace));
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

   private static ArrayList<String> getArrayList(String ... strings) {
      ArrayList<String> temp = new ArrayList<>();
      temp.addAll(Arrays.asList(strings));
      return temp;
   }

   private static String getArrayListString(ArrayList<String> strings) {
      StringBuilder sb = new StringBuilder();
      strings.forEach((string)->{
         sb.append(string);
         sb.append(",");
      });
      sb.deleteCharAt(sb.length()-1); //Delete the extra "," at the end
      return sb.toString();
   }
}
