package flow

import com.coral.base.common.json.JsonUtil

import java.util.stream.Collectors

def rules = MyRule.init();
def age = diagnoseContext.getAge();
def sex = diagnoseContext.getSex();


//根据年龄和性别做数据过滤
rules = rules.stream().filter(e -> {
    def ageMatch = (Objects.nonNull(age) && age >= e.getMinAge() && age <= e.getMaxAge());
    def sexMatch = (Objects.nonNull(sex) && sex.equals(e.getSex()));
    return ageMatch && sexMatch;
}).collect(Collectors.toList());


diagnoseResponse.put(GlobalKey.RULES_KEY, rules);


// 清洗->rule->合并
class MyRule {

    private String name;

    private Integer minAge;

    private Integer maxAge;

    private String sex;

    private String desc;

    MyRule(String name, Integer minAge, Integer maxAge, String sex, String desc) {
        this.name = name
        this.minAge = minAge
        this.maxAge = maxAge
        this.sex = sex
        this.desc = desc
    }

    @Override
    String toString() {
        return JsonUtil.toJson(this);
    }

    static List<MyRule> init() {
        return Arrays.asList(
                new MyRule("规则1", 2, 14, "女", "规则输出一"),
                new MyRule("规则2", 15, 18, "女", "规则输出二"),
                new MyRule("规则3", 25, 44, "女", "规则输出三"),
                new MyRule("规则4", 5, 19, "男", "规则输出四"),
                new MyRule("规则5", 50, 70, "男", "规则输出五"),
                new MyRule("规则6", 18, 65, "男", "规则输出六")
        );

    }

    String getName() {
        return name
    }

    Integer getMinAge() {
        return minAge
    }

    Integer getMaxAge() {
        return maxAge
    }

    String getSex() {
        return sex
    }

    String getDesc() {
        return desc
    }
}


