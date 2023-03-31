package flow

def data = Arrays.asList("血小板", "红细胞", "白细胞", "超敏C");

Thread.sleep(2000)
println("查询检验完成，耗时2秒。数据量：" + data.size());

diagnoseContext.put(GlobalKey.EXAMINES_KEY, data);
