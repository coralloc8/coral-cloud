package flow

import com.coral.base.common.CollectionUtil

def examines = diagnoseContext.find(GlobalKey.EXAMINES_KEY)
return CollectionUtil.isNotBlank(examines);
