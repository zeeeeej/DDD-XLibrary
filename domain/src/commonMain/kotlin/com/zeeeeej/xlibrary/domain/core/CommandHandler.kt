package com.zeeeeej.xlibrary.domain.core

/**
 * 命令处理器
 */
internal interface CommandHandler<Command : DomainCommand> {
    /**
     * [eventQueue] 事件队列
     * [command] 命令
     */
    operator fun invoke(eventQueue: DomainEventQueue, command: Command)
}