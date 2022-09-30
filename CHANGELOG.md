# Change Log
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

## [Version 3.2.1]
### Changed
- Now supports api v2.1
- New Job Service.  Deprecate Old Job Service
- Various Permission DTOs 

## [Version 2.4.2]

### Changed
Updates for:

Clusters
DBFS
Instance Pools
Instance Profiles
Jobs
Libraries

Per:
https://docs.databricks.com/dev-tools/api/latest/index.html

## [Version 2.6.1]

### Changed
DTO classes grouped by packages:

clusters
dbfs
groups
instance_pools
instance_profiles
jobs
libraries
workspace

(packages named in accordance with API sections here https://docs.databricks.com/dev-tools/api/latest/index.html)

ObjectInfo DTO extended with object_id field.
