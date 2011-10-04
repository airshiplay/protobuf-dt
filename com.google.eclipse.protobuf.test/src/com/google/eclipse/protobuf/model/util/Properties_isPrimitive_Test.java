/*
 * Copyright (c) 2011 Google Inc.
 *
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.google.eclipse.protobuf.model.util;

import static com.google.eclipse.protobuf.junit.model.find.PropertyFinder.allPropertiesIn;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.*;

import com.google.eclipse.protobuf.junit.core.XtextRule;
import com.google.eclipse.protobuf.junit.util.MultiLineTextBuilder;
import com.google.eclipse.protobuf.model.util.Properties;
import com.google.eclipse.protobuf.protobuf.*;

/**
 * Tests for <code>{@link Properties#isPrimitive(Property)}</code>.
 *
 * @author alruiz@google.com (Alex Ruiz)
 */
public class Properties_isPrimitive_Test {

  @Rule public XtextRule xtext = XtextRule.unitTestSetup();

  private Properties properties;

  @Before public void setUp() {
    properties = xtext.getInstanceOf(Properties.class);
  }

  @Test public void should_return_true_if_property_is_primitive() {
    MultiLineTextBuilder proto = new MultiLineTextBuilder();
    proto.append("message Primitives {             ")
         .append("  optional float float_1 = 1;    ")
         .append("  optional int32 int32_1 = 2;    ")
         .append("  optional int64 int64_1 = 3;    ")
         .append("  optional uint32 uint32_1 = 4;  ")
         .append("  optional uint64 uint64_1 = 5;  ")
         .append("  optional sint32 sint32_1 = 6;  ")
         .append("  optional sint64 sint64_1 = 7;  ")
         .append("  optional fixed32 fixed32_1 = 8;")
         .append("  optional fixed64 fixed64_1 = 9;")
         .append("  optional bool bool_1 = 10;     ")
         .append("}                                ");
    Protobuf root = xtext.parseText(proto);
    for (Property p : allPropertiesIn(root))
      assertThat(properties.isPrimitive(p), equalTo(true));
  }

  @Test public void should_return_false_if_property_is_not_primitive() {
    MultiLineTextBuilder proto = new MultiLineTextBuilder();
    proto.append("message Types {                  ")
         .append("  optional string string_1 = 1;  ")
         .append("  optional bytes bytes_1 = 2;    ")
         .append("  optional Person person = 3;    ")
         .append("}                                ")
         .append("                                 ")
         .append("message Person {                 ")
         .append("  optional string name = 1;      ")
         .append("}                                ");
    Protobuf root = xtext.parseText(proto);
    for (Property p : allPropertiesIn(root))
      assertThat(properties.isPrimitive(p), equalTo(false));
  }
}