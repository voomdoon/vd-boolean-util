# vd-boolean-util

[![License](https://img.shields.io/github/license/voomdoon/vd-boolean-util)](https://github.com/voomdoon/vd-boolean-util/blob/main/LICENSE)
[![Java](https://img.shields.io/badge/Java-21-blue)](https://adoptium.net/)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=voomdoon_vd-boolean-util&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=voomdoon_vd-boolean-util)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=voomdoon_vd-boolean-util&metric=coverage)](https://sonarcloud.io/summary/new_code?id=voomdoon_vd-boolean-util)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=voomdoon_vd-boolean-util&metric=bugs)](https://sonarcloud.io/summary/new_code?id=voomdoon_vd-boolean-util)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=voomdoon_vd-boolean-util&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=voomdoon_vd-boolean-util)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=voomdoon_vd-boolean-util&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=voomdoon_vd-boolean-util)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=voomdoon_vd-boolean-util&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=voomdoon_vd-boolean-util)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=voomdoon_vd-boolean-util&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=voomdoon_vd-boolean-util)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=voomdoon_vd-boolean-util&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=voomdoon_vd-boolean-util)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=voomdoon_vd-boolean-util&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=voomdoon_vd-boolean-util)

Utility classes for boolean arrays and boolean matrices.

## Installation

```xml
<dependency>
	<groupId>de.voomdoon.util</groupId>
	<artifactId>vd-boolean-util</artifactId>
	<version>0.1.0</version>
</dependency>
```

## Usage

### Count `true` Values

Count `true` values in a boolean array:

```java
int count = BooleanArrayUtil.countTrue(new boolean[] { true, false, true });
```

### Format Boolean Matrices

Format a boolean matrix:

```java
boolean[][] matrix = {
	{ true, false },
	{ false, true }
};

String formatted = BooleanMatrixFormatter.builder()
	.withFormat(BooleanMatrixFormatter.Format.ONE_AND_ZERO)
	.build()
	.format(matrix);
```

The same matrix can be rendered in different formats:

`ONE_AND_ZERO`

```text
10
01
```

`TRUE_AND_FALSE_WITH_SEPARATOR`

```text
true,false
false,true
```

`DOUBLE_WIDTH_BLOCKS`

```text
██  
  ██
```

`HALF_BLOCKS`

```text
▀▄
```
