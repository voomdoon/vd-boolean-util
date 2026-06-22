# vd-boolean-util

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
