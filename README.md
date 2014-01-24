ipslibrary
==========

MAVEN ARTIFACT DEPLOYMENT
=========================


Create a setting.xml file into the pom.xml's directiory.

The file's content should be:

```xml
<settings>
  <servers>
    <server>
      <id>github</id>
      <username>your_username</username>
      <password>your_password</password>
    </server>
  </servers>
</settings>
```


Now launch 'clean and build' and then run the goal 'deploy'
