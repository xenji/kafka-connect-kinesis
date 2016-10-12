/*
 * Copyright 2016 David Tucker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dbtucker.connect.kinesis;

import com.amazonaws.regions.Regions;
import org.apache.kafka.common.config.AbstractConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KinesisSourceTaskConfig extends AbstractConfig {
    enum CfgKeys {
        ;
        static String REGION = "region";
        static String TOPIC_FORMAT = "topic.format";
        static String REC_PER_REQ = "records.per.request";
        static String SHARDS = "shards";
        static String STREAM = "stream";
        static String STREAM_ARN = "stream.arn";
    }

    final List<String> shards;

    KinesisSourceTaskConfig(Map<String, String> props) {
        super((Map) props);
        shards = Arrays.stream(getString(CfgKeys.SHARDS).split(",")).filter(shardId -> !shardId.isEmpty()).collect(Collectors.toList());
    }

    public String getRegion() {
        return this.getString(CfgKeys.REGION);
    }

    public Regions getRegionId() {
        return Regions.fromName(this.getString(CfgKeys.REGION));
    }

    public String getTopicFormat(){
        return this.getString(CfgKeys.TOPIC_FORMAT);
    }

    public Integer getRecPerReq(){
        return (Integer.valueOf(getString(CfgKeys.REC_PER_REQ)));
    }

    public List<String> getShards() {
        return this.shards;
    }

    String streamForShard(String shardId) {
        return getString(shardId + "." + CfgKeys.STREAM);
    }
}

