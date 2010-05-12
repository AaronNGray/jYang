../../pyangTests/test_bad/a.yang
a.yang:10;5:typedef xx is already defined at a.yang:9
a.yang:12;5:circular dependency for type "ba"
a.yang:14;5:circular dependency for type "bb"
a.yang:16;5:circular dependency for type "u1"
a.yang:22;5:circular dependency for type "u2"
a.yang:37;13:restriction length is not allowed for the base type int32
a.yang:50;13:default value "3" does not match its base type at a.yang:41 - range error for the inherited default value for range defined at a.yang:41 
a.yang:59;9:default value "ac" does not match its base type - pattern mismatch for the default value for pattern defined at a.yang:57 
a.yang:66;17:the enum value "0" has already been used for the enumeration at a.yang:64
a.yang:71;13:the enum name "foo" has already been used for the enumeration at a.yang:64
a.yang:83;17:the position 3 has already been used for the bit at a.yang:81
a.yang:100;13:the type empty (defined at a.yang:105) cannot be part of union
a.yang:108;13:the type empty (defined at a.yang:112) cannot be part of union
a.yang:140;5:recursive grouping uses from "a:bar"
a.yang:143;5:recursive grouping uses from "foox"
a.yang:148;7:bad enum expression : 18446744073709551613
a.yang:158;7:automatic enum value too big for unknown
d.yang:32;13:duplicate child name : y is already defined at d.yang:18
d.yang:36;17:duplicate child name : z3 is already defined at d.yang:25
d.yang:40;13:duplicate child name : case z is already defined at d.yang:35
d.yang:46;9:duplicate child name : a is already defined at d.yang:17
d.yang:49;9:duplicate child name : z1 is already defined at d.yang:19
d.yang:54;13:default value : apa is not an integer
d.yang:56;11:duplicate child name : z2 is already defined at d.yang:53
d.yang:64;7:mandatory is "true" and default exists at d.yang:65
e.yang:57;7:node s is config "true" and its parent is config "false"
e.yang:69;5:the node e at e.yang:91 can not be augmented
e.yang:76;5:the node g at e.yang:42 can not be augmented
e.yang:82;5:augmented node /e:y/e:z/e:q/e:g/e:r not found
f.yang:11;3:config of the list gg is "true" but no key is defined
f.yang:17;3:node x is config "true" and its parent is config "false"
f.yang:36;5:empty type for the key z2 of the list ff
f.yang:38;5:unique node foo not found in the list ff
../../pyangTests/test_bad/augment-super.yang
augment-super.yang:8;9:duplicate child name : ifEntry is already defined at augment.yang:7
../../pyangTests/test_bad/augment.yang
../../pyangTests/test_bad/b.yang
a.yang:10;5:typedef xx is already defined at a.yang:9
a.yang:12;5:circular dependency for type "ba"
a.yang:14;5:circular dependency for type "bb"
a.yang:16;5:circular dependency for type "u1"
a.yang:22;5:circular dependency for type "u2"
a.yang:37;13:restriction length is not allowed for the base type int32
a.yang:50;13:default value "3" does not match its base type at a.yang:41 - range error for the inherited default value for range defined at a.yang:41 
a.yang:59;9:default value "ac" does not match its base type - pattern mismatch for the default value for pattern defined at a.yang:57 
a.yang:66;17:the enum value "0" has already been used for the enumeration at a.yang:64
a.yang:71;13:the enum name "foo" has already been used for the enumeration at a.yang:64
a.yang:83;17:the position 3 has already been used for the bit at a.yang:81
a.yang:100;13:the type empty (defined at a.yang:105) cannot be part of union
a.yang:108;13:the type empty (defined at a.yang:112) cannot be part of union
a.yang:140;5:recursive grouping uses from "a:bar"
a.yang:143;5:recursive grouping uses from "foox"
a.yang:148;7:bad enum expression : 18446744073709551613
a.yang:158;7:automatic enum value too big for unknown
d.yang:32;13:duplicate child name : y is already defined at d.yang:18
d.yang:36;17:duplicate child name : z3 is already defined at d.yang:25
d.yang:40;13:duplicate child name : case z is already defined at d.yang:35
d.yang:46;9:duplicate child name : a is already defined at d.yang:17
d.yang:49;9:duplicate child name : z1 is already defined at d.yang:19
d.yang:54;13:default value : apa is not an integer
d.yang:56;11:duplicate child name : z2 is already defined at d.yang:53
d.yang:64;7:mandatory is "true" and default exists at d.yang:65
e.yang:57;7:node s is config "true" and its parent is config "false"
e.yang:69;5:the node e at e.yang:91 can not be augmented
e.yang:76;5:the node g at e.yang:42 can not be augmented
e.yang:82;5:augmented node /e:y/e:z/e:q/e:g/e:r not found
f.yang:11;3:config of the list gg is "true" but no key is defined
f.yang:17;3:node x is config "true" and its parent is config "false"
f.yang:36;5:empty type for the key z2 of the list ff
f.yang:38;5:unique node foo not found in the list ff
../../pyangTests/test_bad/bad-uses.yang
bad-uses.yang:6;5:keyword type expected
bad-uses.yang:7;7:unexpected keyword "uses"
../../pyangTests/test_bad/c.yang
a.yang:10;5:typedef xx is already defined at a.yang:9
a.yang:12;5:circular dependency for type "ba"
a.yang:14;5:circular dependency for type "bb"
a.yang:16;5:circular dependency for type "u1"
a.yang:22;5:circular dependency for type "u2"
a.yang:37;13:restriction length is not allowed for the base type int32
a.yang:50;13:default value "3" does not match its base type at a.yang:41 - range error for the inherited default value for range defined at a.yang:41 
a.yang:59;9:default value "ac" does not match its base type - pattern mismatch for the default value for pattern defined at a.yang:57 
a.yang:66;17:the enum value "0" has already been used for the enumeration at a.yang:64
a.yang:71;13:the enum name "foo" has already been used for the enumeration at a.yang:64
a.yang:83;17:the position 3 has already been used for the bit at a.yang:81
a.yang:100;13:the type empty (defined at a.yang:105) cannot be part of union
a.yang:108;13:the type empty (defined at a.yang:112) cannot be part of union
a.yang:140;5:recursive grouping uses from "a:bar"
a.yang:143;5:recursive grouping uses from "foox"
a.yang:148;7:bad enum expression : 18446744073709551613
a.yang:158;7:automatic enum value too big for unknown
d.yang:32;13:duplicate child name : y is already defined at d.yang:18
d.yang:36;17:duplicate child name : z3 is already defined at d.yang:25
d.yang:40;13:duplicate child name : case z is already defined at d.yang:35
d.yang:46;9:duplicate child name : a is already defined at d.yang:17
d.yang:49;9:duplicate child name : z1 is already defined at d.yang:19
d.yang:54;13:default value : apa is not an integer
d.yang:56;11:duplicate child name : z2 is already defined at d.yang:53
d.yang:64;7:mandatory is "true" and default exists at d.yang:65
e.yang:57;7:node s is config "true" and its parent is config "false"
e.yang:69;5:the node e at e.yang:91 can not be augmented
e.yang:76;5:the node g at e.yang:42 can not be augmented
e.yang:82;5:augmented node /e:y/e:z/e:q/e:g/e:r not found
f.yang:11;3:config of the list gg is "true" but no key is defined
f.yang:17;3:node x is config "true" and its parent is config "false"
f.yang:36;5:empty type for the key z2 of the list ff
f.yang:38;5:unique node foo not found in the list ff
../../pyangTests/test_bad/composite.yang
composite.yang:8;3:the name crypto-base is not a sub-module
composite.yang:10;3:typedef type1 is already defined at submodule1.yang:7
composite.yang:14;3:identity id1 is already defined at submodule1.yang:15
composite.yang:16;3:feature f1 is already defined at submodule1.yang:17
submodule2.yang:7;3:the name submodule1 is not a module name
submodule2.yang:13;3:duplicate child name : g1 is already defined at submodule1.yang:19
../../pyangTests/test_bad/crypto-base.yang
../../pyangTests/test_bad/d.yang
d.yang:32;13:duplicate child name : y is already defined at d.yang:18
d.yang:36;17:duplicate child name : z3 is already defined at d.yang:25
d.yang:40;13:duplicate child name : case z is already defined at d.yang:35
d.yang:46;9:duplicate child name : a is already defined at d.yang:17
d.yang:49;9:duplicate child name : z1 is already defined at d.yang:19
d.yang:54;13:default value : apa is not an integer
d.yang:56;11:duplicate child name : z2 is already defined at d.yang:53
d.yang:64;7:mandatory is "true" and default exists at d.yang:65
e.yang:57;7:node s is config "true" and its parent is config "false"
e.yang:69;5:the node e at e.yang:91 can not be augmented
e.yang:76;5:the node g at e.yang:42 can not be augmented
e.yang:82;5:augmented node /e:y/e:z/e:q/e:g/e:r not found
f.yang:11;3:config of the list gg is "true" but no key is defined
f.yang:17;3:node x is config "true" and its parent is config "false"
f.yang:36;5:empty type for the key z2 of the list ff
f.yang:38;5:unique node foo not found in the list ff
../../pyangTests/test_bad/e.yang
e.yang:57;7:node s is config "true" and its parent is config "false"
e.yang:69;5:the node e at e.yang:91 can not be augmented
e.yang:76;5:the node g at e.yang:42 can not be augmented
e.yang:82;5:augmented node /e:y/e:z/e:q/e:g/e:r not found
f.yang:11;3:config of the list gg is "true" but no key is defined
f.yang:17;3:node x is config "true" and its parent is config "false"
f.yang:36;5:empty type for the key z2 of the list ff
f.yang:38;5:unique node foo not found in the list ff
../../pyangTests/test_bad/f.yang
f.yang:11;3:config of the list gg is "true" but no key is defined
f.yang:17;3:node x is config "true" and its parent is config "false"
f.yang:36;5:empty type for the key z2 of the list ff
f.yang:38;5:unique node foo not found in the list ff
../../pyangTests/test_bad/g.yang
g.yang:12;9:config of the list a is "true" but no key is defined
g.yang:30;15:unexpected keyword "d"
g.yang:34;5:augmented node /foo/d/x not found
g.yang:47;17:default value "-1" does not match its base type at g.yang:41 - range error for the inherited default value for range defined at g.yang:42 
g.yang:58;17:default value "1" does not match its base type - range error for the default value for range defined at g.yang:52 
g.yang:64;21:default value "2" does not match its base type - range error for the default value for range defined at g.yang:52 
g.yang:68;9:unexpected keyword "input"
../../pyangTests/test_bad/id.yang
id.yang:9;3:circular feature references for "x" from id.yang:13
id.yang:13;3:circular feature references for "y" from id.yang:9
id.yang:18;5:feature "x2" not found
id.yang:21;3:feature z is already defined at id.yang:17
id.yang:29;5:feature "x2" not found
id.yang:33;3:circular identity reference for "x"
id.yang:37;3:circular identity reference for "y"
id.yang:41;3:identity y is already defined at id.yang:37
id.yang:54;5:base identity "crypto:crypto-alg2" not found
id.yang:59;5:base identity "crypto-alg2" not found
id.yang:61;5:unexpected keyword "base"
../../pyangTests/test_bad/infinite-loop.yang
infinite-loop.yang:7;5:recursive grouping uses from "target"
../../pyangTests/test_bad/list2.yang
list2.yang:8;7:mandatory node "node" in the default case of the choice "test" at list2.yang:5
list2.yang:12;7:mandatory node "mandatory2" in the default case of the choice "test" at list2.yang:5
list2.yang:21;11:mandatory node "m" in the default case of the choice "test" at list2.yang:5
../../pyangTests/test_bad/lists.yang
lists.yang:13;5:config value of the key "observed-speed" is not the same as the list interface at lists.yang:5
../../pyangTests/test_bad/rpc.yang
rpc.yang:6;9:a rpc input must have at least one data definition
rpc.yang:8;9:a rpc output must have at least one data definition
../../pyangTests/test_bad/separator.yang
../../pyangTests/test_bad/smi1.yang
../../pyangTests/test_bad/submodule1.yang
../../pyangTests/test_bad/submodule2.yang
submodule2.yang:7;3:the name submodule1 is not a module name
../../pyangTests/test_bad/submodule3.yang
../../pyangTests/test_bad/xt1.yang
xt1.yang:8;3:the sub-module xt1s2 does not belongs to module xt1
xt1.yang:24;3:illegal uses of built-in type uint16
xt1.yang:82;5:default value "bbb" does not match its base type at xt1.yang:54 - pattern mismatch for the inherited default value for pattern defined at xt1.yang:58 
xt1.yang:128;7:duplicate child name : qwerty is already defined at xt1.yang:122
xt1.yang:140;7:default case c of the choice xx is not defined
xt1.yang:195;5:duplicate child name : err1 is already defined at xt1.yang:191
xt1.yang:205;5:duplicate child name : err2 is already defined at xt1.yang:202
xt1.yang:216;5:config of the list a is "true" but no key is defined
xt1.yang:225;5:bad order in range : 5 > 2
xt1.yang:231;5:bad order in range : 5 > 2
xt1.yang:244;3:keyword type expected
xt1.yang:265;13:default value "a..b" does not match its base type - length error for the default value for length defined at xt1.yang: 
xt1.yang:271;13:default value "a...b" does not match its base type - length error for the default value for length defined at xt1.yang: 
xt1.yang:275;13:default value "a.x" does not match its base type at xt1.yang:250 - pattern mismatch for the inherited default value for pattern defined at xt1.yang:253 
xt1.yang:285;5:augmented node /sub2 not found
xt1.yang:305;7:the node r at xt1.yang:303 can not be augmented
xt1.yang:311;9:duplicate child name : c is already defined at xt1.yang:295
xt1.yang:320;7:bad uses augment expression : /bar/interface
xt3.yang:2;1:prefix expected
xt4.yang:12;5:too many fraction digit : 1.506 for 2 digit(s) max
xt4.yang:25;5:subtyping error -INF..3.11
xt4.yang:26;7:default value "3.14" does not match its base type at xt4.yang:18 - range error for the inherited default value for range defined at xt4.yang:25 
xt4.yang:31;5:require-instance is not allowed for the base type dc1
xt4.yang:70;7:illegal delete a different default statement from the node a at xt4.yang:52
xt4.yang:71;8:illegal delete a different must statement from the node a at xt4.yang:52
../../pyangTests/test_bad/xt1s1.yang
xt1.yang:320;7:bad uses augment expression : /bar/interface
xt3.yang:2;1:prefix expected
xt4.yang:12;5:too many fraction digit : 1.506 for 2 digit(s) max
xt4.yang:25;5:subtyping error -INF..3.11
xt4.yang:26;7:default value "3.14" does not match its base type at xt4.yang:18 - range error for the inherited default value for range defined at xt4.yang:25 
xt4.yang:31;5:require-instance is not allowed for the base type dc1
xt4.yang:70;7:illegal delete a different default statement from the node a at xt4.yang:52
xt4.yang:71;8:illegal delete a different must statement from the node a at xt4.yang:52
../../pyangTests/test_bad/xt1s2.yang
xt1s2:1;1:file "foo".yang not found
xt1s2.yang:2;3:the name foo is not a module name
../../pyangTests/test_bad/xt2.yang
../../pyangTests/test_bad/xt3.yang
xt3.yang:2;1:prefix expected
xt4.yang:12;5:too many fraction digit : 1.506 for 2 digit(s) max
xt4.yang:25;5:subtyping error -INF..3.11
xt4.yang:26;7:default value "3.14" does not match its base type at xt4.yang:18 - range error for the inherited default value for range defined at xt4.yang:25 
xt4.yang:31;5:require-instance is not allowed for the base type dc1
xt4.yang:70;7:illegal delete a different default statement from the node a at xt4.yang:52
xt4.yang:71;8:illegal delete a different must statement from the node a at xt4.yang:52
../../pyangTests/test_bad/xt4.yang
xt4.yang:12;5:too many fraction digit : 1.506 for 2 digit(s) max
xt4.yang:25;5:subtyping error -INF..3.11
xt4.yang:26;7:default value "3.14" does not match its base type at xt4.yang:18 - range error for the inherited default value for range defined at xt4.yang:25 
xt4.yang:31;5:require-instance is not allowed for the base type dc1
xt4.yang:70;7:illegal delete a different default statement from the node a at xt4.yang:52
xt4.yang:71;8:illegal delete a different must statement from the node a at xt4.yang:52
../../pyangTests/test_bad/yt1.yang
yt1.yang:22;5:default value "7" does not match its base type - range error for the default value for range defined at yt1.yang:25 
yt1.yang:38;9:default value : foo is not an integer
../../pyangTests/test_bad/yt2.yang
yt1.yang:22;5:default value "7" does not match its base type - range error for the default value for range defined at yt1.yang:25 
yt1.yang:38;9:default value : foo is not an integer
../../pyangTests/test_bad/yt3a.yang
../../pyangTests/test_bad/yt4.yang
yt4.yang:26;11:type yt3:Num3 is not defined in the current context
../../pyangTests/test_bad/yt5a.yang
yt5a.yang:78;9:type yt3:t10 is not defined in the current context
