package featureExtraction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Just replaces some things from BiFlowExtraction
 * @author 
 */
public class CaidaNormalBiFlowExtraction extends BiFlowExtraction{
   @Override
   public String getName() {
      return "CaidaBiFlow";
   }


   @Override
   public List<String> getFeaturesToRemove() {
      ArrayList<String> al = new ArrayList<>();
      al.add("src_ip");
      al.add("dst_ip");
      al.add("proto_number");
      al.add("src_port");
      al.add("dst_port");

   //Features that can't be extracted from the Caida dataset
      al.add("total_b_packets");
      al.add("total_b_bytes");
      al.add("pps_b");
      al.add("bps_b");
      al.add("rfb_packets");
      al.add("rfb_bytes");
      al.add("min_b_pkt");
      al.add("mean_b_pkt");
      al.add("max_b_pkt");
      al.add("std_b_pkt");
      al.add("min_b_time");
      al.add("mean_b_time");
      al.add("max_b_time");
      al.add("std_b_time");
      al.add("psh_b_cnt");
      al.add("urg_b_cnt");

      return Collections.unmodifiableList(al);
   }
}