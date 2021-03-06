/*
 * MIT License
 *
 * Copyright (c) 2018 Kapralov Sergey
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.github.skapral.puzzler.github.operation;

import java.io.InputStream;

import com.github.skapral.puzzler.core.config.CpMissingValue;
import com.github.skapral.puzzler.core.config.CpStatic;
import com.github.skapral.puzzler.core.operation.AssertOperationFails;
import com.github.skapral.puzzler.core.operation.AssertOperationSuccessful;
import com.github.skapral.puzzler.core.operation.OpDoNothing;
import com.pragmaticobjects.oo.tests.TestCase;
import com.pragmaticobjects.oo.tests.junit5.TestsSuite;
import org.apache.commons.io.IOUtils;

/**
 * Tests suite for {@link OpValidatingGithubEventSignature}
 *
 * @author Kapralov Sergey
 */
class OpValidatingGithubEventSignatureTest extends TestsSuite {
    private static final String SIGNED_BODY;

    static {
        try (InputStream signedBodyStream = OpValidatingGithubEventSignature.class.getResource("signedBody").openStream()) {
            SIGNED_BODY = IOUtils.toString(signedBodyStream, "UTF-8");
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Ctor.
     */
    public OpValidatingGithubEventSignatureTest() {
        super(
            new TestCase(
                "operation passes if signatures match",
                new AssertOperationSuccessful(
                    new OpValidatingGithubEventSignature(
                        new CpStatic("secret"),
                        SIGNED_BODY,
                        "sha1=e7e81bca3c1c67910123611b1d94bfe78760b9dd",
                        new OpDoNothing(),
                        RuntimeException::new
                    )
                )
            ),
            new TestCase(
                "operation fails if signatures mismatch",
                new AssertOperationFails(
                    new OpValidatingGithubEventSignature(
                        new CpStatic("secret"),
                        SIGNED_BODY,
                        "sha1=somefakesignature00000000000000000000000",
                        new OpDoNothing(),
                        RuntimeException::new
                    )
                )
            ),
            new TestCase(
                "signatures are not validated if secret is not provided",
                new AssertOperationSuccessful(
                    new OpValidatingGithubEventSignature(
                        new CpMissingValue(),
                        SIGNED_BODY,
                        "sha1=somefakesignature00000000000000000000000",
                        new OpDoNothing(),
                        RuntimeException::new
                    )
                )
            )
        );
    }

}