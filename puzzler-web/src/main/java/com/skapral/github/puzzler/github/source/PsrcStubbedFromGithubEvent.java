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

package com.skapral.github.puzzler.github.source;

import com.github.skapral.puzzler.core.Puzzle;
import com.github.skapral.puzzler.core.PuzzleSource;
import com.github.skapral.puzzler.core.puzzle.PzlStatic;
import io.vavr.collection.List;
import org.json.JSONObject;

/**
 * Puzzle source which just creates two new issues with dummy title and description once one is closed.
 * Used as a stub for the initial prototype, to be replaced in future.
 *
 * @author Kapralov Sergey
 */
public class PsrcStubbedFromGithubEvent implements PuzzleSource {
    private final String eventType;
    private final String eventBody;

    /**
     * Ctor.
     *
     * @param eventType Github event's type (usually passed via X-GitHub-Event header).
     * @param eventBody Github event's body in JSON format.
     */
    public PsrcStubbedFromGithubEvent(String eventType, String eventBody) {
        this.eventType = eventType;
        this.eventBody = eventBody;
    }

    @Override
    public final List<Puzzle> puzzles() {
        final JSONObject object = new JSONObject(eventBody);
        if(!object.getString("action").equals("closed")) {
            return List.empty();
        }
        /*final String url = object.getJSONObject("issue").getString("comments_url");
        System.out.println(url);*/
        return List.of(new PzlStatic("test", "descr"), new PzlStatic("test", "descr"));
    }
}