package com.example.docs.codec;

import java.util.Optional;

// #region node_record
public record ListNode(int value, Optional<ListNode> next) {
}
// #endregion node_record
