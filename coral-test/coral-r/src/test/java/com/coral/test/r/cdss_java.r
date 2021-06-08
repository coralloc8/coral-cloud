CDSS_stat <- function(dept_name, doctor_name, message, data_name, time_select){
  #CDSS功能统计，dept_name为筛选科室，doctor_name为筛选医生，message为筛选场景，data_name为筛选功能
  #time_select为筛选时间
  
  #连接数据库
  
  library("RMySQL")
  
  conn <- dbConnect(MySQL(), host = '192.168.29.82', dbname = 'zhyx_manage', 
                    user = 'zhyx',  password = 'zhyx',port=3306) #连接MySQL
  
  dbSendQuery(conn,'SET NAMES gbk') #中文编码转换
  
  decision_log <- dbReadTable(conn, "emr_log_decision")  #读CDSS决策日志表
  
  decision_log_sel <- data.frame(decision_log$id, decision_log$req_time, decision_log$dept_name_now,
                                 decision_log$doctor_name_now, decision_log$patient_name ,decision_log$message,
                                 decision_log$data_name, decision_log$tips_type, decision_log$title_one,
                                 decision_log$req_month, decision_log$req_day, substr(decision_log$req_time,0,4))  
  
  colnames(decision_log_sel) <- c("id", "req_time","dept_name_now", "doctor_name_now","patient_name", "message", "data_name",
                                  "tips_type","title_one","req_month","req_day","req_year")
  
  today <- Sys.Date()
  
  yesterday <- Sys.Date()-1
  
  day_7 <- c((Sys.Date()-7):Sys.Date())
  
  day_month <- c((Sys.Date()-30):Sys.Date())
  
  decision_log_sel$req_day <- as.Date(decision_log_sel$req_day)
  
  decision_log_sel <- decision_log_sel[which(decision_log_sel$dept_name_now == dept_name),] #筛选科室
  
  decision_log_sel <- decision_log_sel[which(decision_log_sel$doctor_name_now == doctor_name),] #筛选医生
  
  decision_log_sel <- decision_log_sel[which(decision_log_sel$message == message),] #筛选场景
  
  decision_log_sel <- decision_log_sel[which(decision_log_sel$data_name == data_name),] #筛选功能
  
  
  if(time_select=="今天"){
    
    decision_log_sel <- decision_log_sel[which(decision_log_sel$req_day == today),] #筛选今日时间
    
    return(decision_log_sel)
  }
  
  if(time_select=="昨天"){
    
    decision_log_sel <- decision_log_sel[which(decision_log_sel$req_day == yesterday),] #筛选昨天时间
    
    return(decision_log_sel)
    
  }
  
  if(time_select=="最近7天"){
    
    decision_log_sel <- decision_log_sel[which(as.numeric(decision_log_sel$req_day) %in% day_7),] #筛选最近7天
    
    return(decision_log_sel)
    
  }
  
  if(time_select=="最近一个月"){
    
    decision_log_sel <- decision_log_sel[which(as.numeric(decision_log_sel$req_day) %in% day_month),] #筛选最近一个月
    
    return(decision_log_sel)
    
  }
  
  
  
}

# CDSS_stat("妇科","赖紫玲","查看检验报告","（警告）多正常值检验项目异常结果警示","最近一个月")




