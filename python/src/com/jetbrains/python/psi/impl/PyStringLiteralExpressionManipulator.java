/*
 * Copyright 2000-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jetbrains.python.psi.impl;

import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.AbstractElementManipulator;
import com.intellij.util.IncorrectOperationException;
import com.jetbrains.python.psi.PyElementGenerator;
import com.jetbrains.python.psi.PyStringLiteralExpression;
import com.jetbrains.python.psi.PyStringLiteralUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author traff
 */
public class PyStringLiteralExpressionManipulator extends AbstractElementManipulator<PyStringLiteralExpressionImpl> {

  @Override
  public PyStringLiteralExpressionImpl handleContentChange(@NotNull PyStringLiteralExpressionImpl element,
                                                           @NotNull TextRange range,
                                                           String newContent) {
    final String newName = range.replace(element.getText(), newContent);

    final PyElementGenerator elementGenerator = PyElementGenerator.getInstance(element.getProject());
    final PyStringLiteralExpression escaped = elementGenerator.createStringLiteralAlreadyEscaped(newName);

    return (PyStringLiteralExpressionImpl)element.replace(escaped);
  }

  @Override
  public PyStringLiteralExpressionImpl handleContentChange(@NotNull PyStringLiteralExpressionImpl element, String newContent)
    throws IncorrectOperationException {
    return handleContentChange(element, super.getRangeInElement(element), newContent);
  }

  @NotNull
  @Override
  public TextRange getRangeInElement(@NotNull PyStringLiteralExpressionImpl element) {
    final Pair<String, String> pair = PyStringLiteralUtil.getQuotes(element.getText());
    if (pair != null) {
      return TextRange.from(pair.first.length(), element.getTextLength() - pair.first.length() - pair.second.length());
    }
    return super.getRangeInElement(element);
  }
}
