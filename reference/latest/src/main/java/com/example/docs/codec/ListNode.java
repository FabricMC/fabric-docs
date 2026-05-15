package com.example.docs.codec;

import java.util.Optional;

// #region node-record
public record ListNode(int value, Optional<ListNode> next) {
}
// #endregion node-record
