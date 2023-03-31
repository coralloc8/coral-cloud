package flow

import com.coral.base.common.CollectionUtil

def examines = diagnoseContext.find(GlobalKey.EXAMINES_KEY)

if (CollectionUtil.isNotBlank(examines) && examines.contains("血小板")) {
    diagnoseResponse.put(GlobalKey.EXAMINES_KEY, Arrays.asList("血小板"));
}
