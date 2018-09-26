# Contributing

When contributing to this repository, please first discuss the change you wish to make via issue,
email, or any other method with the owners of this repository before making a change. 

## We Develop with Github
We use github to host code, to track issues and feature requests, as well as accept pull requests.

### Customizing .git/config
Please make sure that you are using a valid github account when contributing to this repo.
If the user/password differs then that of your global .git/config, 
add following the .git/config of your repo:
```yaml
[user]
	name = {git user}
	email = {git email}
```


## We Use [Github Flow](https://guides.github.com/introduction/flow/index.html), So All Code Changes Happen Through Pull Requests
Pull requests are the best way to propose changes to the codebase (we use [Github Flow](https://guides.github.com/introduction/flow/index.html)). We actively welcome your pull requests:

1. Fork the repo and create your branch from `master`. Branch names should be like issue5/updating-workspace-service.
2. If you've added code that should be tested, add tests.
3. If you've changed APIs, update the README.
4. Ensure the test suite passes.
5. Run any integration tests and make sure they pass.
6. Issue that pull request!
7. Pull requests should be reviewed by at least 1 other developer before being merged

## Any contributions you make will be under the Apache License 2.0
In short, when you submit code changes, your submissions are understood to be under the same [Apache License 2.0](https://choosealicense.com/licenses/apache-2.0/) that covers the project. Feel free to contact the maintainers if that's a concern.

## Report bugs and request features using Github's [issues](https://github.com/edmunds/databricks-rest-client/issues)
We use GitHub issues to track public bugs. Report a bug or request a new feature by [opening a new issue](); it's that easy!

### Write bug reports with detail, background, and sample code

**Great Bug Reports** tend to have:

- A quick summary and/or background
- Steps to reproduce
  - Be specific!
  - Give sample code if you can.
- What you expected would happen
- What actually happens
- Notes (possibly including why you think this might be happening, or stuff you tried that didn't work)

People *love* thorough bug reports. I'm not even kidding.

## Making Releases to Maven Central
Only Certain Edmunds Developers will be able to make releases of a new version to Maven Central.
Here are the steps to be able to make a release:
### Setup
1. Create an account on issues.sonatype.org
2. Respond to this ticket saying that your username needs permission:
https://issues.sonatype.org/browse/OSSRH-42546
3. Add to your ~/.m2/settings.xml
```xml 
<server>
  <id>ossrh</id>
  <username>{sonatype username}</username>
  <password>{sonatype password}</password>
</server>
``` 

4. [Install GPG](https://central.sonatype.org/pages/working-with-pgp-signatures.html) and make a key.
5. Upload your key to a couple of servers:
```bash
gpg --keyserver hkp://pool.sks-keyservers.net --send-keys ${key-id}
gpg --keyserver hkp://keyserver.ubuntu.com --send-keys ${key-id}
```
### Performing a Release
Execute
```bash
mvn release:clean release:prepare release:perform -Pdeploy
```
[Verify](https://oss.sonatype.org/#nexus-search;quick~databricks-rest-client)


## Use a Consistent Coding Style
We use a slight modification of google java style.
Please configure your setup to use the checkstyle/google_checkstyle.xml files
(For Intellij users, you should use the google-idea-checkstyle.xml for your auto-formatting)
Currently, builds are configured to fail if style requirements are not met.

## References
This document was adapted from the open-source contribution guidelines for [Transcriptase](https://gist.github.com/briandk/3d2e8b3ec8daf5a27a62)