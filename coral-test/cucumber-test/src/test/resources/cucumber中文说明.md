# 文档说明

[官方文档地址](https://cucumber.io/docs/gherkin/reference/#example)

### 关键词说明

| 标题                 | 中文标题                   |
|:-------------------|:-----------------------|
| Feature            | "功能"                   |
| Background         | "背景"                   |
| Rule               | "Rule","规则"            |
| Scenario/Example   | "场景", "剧本"             |
| ScenarioOutline    | "场景大纲", "剧本大纲"         |
| Examples/Scenarios | "例子"                   |
| Given              | "* ", "假如", "假设", "假定" |
| When               | "* ", "当"              |
| Then               | "* ", "那么"             |
| And                | "* ", "而且", "并且", "同时" |
| But                | "* ", "但是"             |

***

# Tips for using Background

- Don’t use Background to set up complicated states, unless that state is actually something the client needs to know.

  > + For example, if the user and site names don’t matter to the client, use a higher-level step such as Given I am
      logged in as a site owner.

- Keep your Background section short.

  > + The client needs to actually remember this stuff when reading the scenarios. If the Background is more than 4
      lines
      long, consider moving some of the irrelevant details into higher-level steps.

- Make your Background section vivid.

  > + Use colourful names, and try to tell a story. The human brain keeps track of stories much better than it keeps
      track of names like "User A", "User B", "Site 1", and so on.

- Keep your scenarios short, and don’t have too many.

  > + If the Background section has scrolled off the screen, the reader no longer has a full overview of what’s
      happening. Think about using higher-level steps, or splitting the *.feature file.

### 示例

```
Feature: Multiple site support
  Only blog owners can post to a blog, except administrators,
  who can post to all blogs.

  Background:
    Given a global administrator named "Greg"
    And a blog named "Greg's anti-tax rants"
    And a customer named "Dr. Bill"
    And a blog named "Expensive Therapy" owned by "Dr. Bill"

  Scenario: Dr. Bill posts to his own blog
    Given I am logged in as Dr. Bill
    When I try to post to "Expensive Therapy"
    Then I should see "Your article was published."

  Scenario: Dr. Bill tries to post to somebody else's blog, and fails
    Given I am logged in as Dr. Bill
    When I try to post to "Greg's anti-tax rants"
    Then I should see "Hey! That's not your blog!"

  Scenario: Greg posts to a client's blog
    Given I am logged in as Greg
    When I try to post to "Expensive Therapy"
    Then I should see "Your article was published."

```

```

Scenario Outline: eating
  Given there are <start> cucumbers
  When I eat <eat> cucumbers
  Then I should have <left> cucumbers

  Examples:
    | start | eat | left |
    |    12 |   5 |    7 |
    |    20 |   5 |   15 |



```
