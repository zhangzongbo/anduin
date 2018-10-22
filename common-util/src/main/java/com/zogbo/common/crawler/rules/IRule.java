package com.zogbo.common.crawler.rules;

/**
 * Created by cw on 15-9-15.
 */
public interface IRule<B,C> {
    C isConformRule(B data);
}
