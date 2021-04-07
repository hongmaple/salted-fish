package com.haiyan.deflower.exception;

/**
 * 基础异常
 * 
 * @author haiyan
 */
public class ExceptionResult extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String defaultMessage;

    public ExceptionResult(String module, String code, Object[] args, String defaultMessage)
    {
        this.module = module;
        this.code = code;
        this.args = args;
        this.defaultMessage = defaultMessage;
    }

    public ExceptionResult(String module, String code, Object[] args)
    {
        this(module, code, args, null);
    }

    public ExceptionResult(String module, String defaultMessage)
    {
        this(module, null, null, defaultMessage);
    }

    public ExceptionResult(String code, Object[] args)
    {
        this(null, code, args, null);
    }

    public ExceptionResult(String defaultMessage)
    {
        this(null, null, null, defaultMessage);
    }

    @Override
    public String getMessage()
    {
        String message = null;
        if (message == null)
        {
            message = defaultMessage;
        }
        return message;
    }

    public String getModule()
    {
        return module;
    }

    public String getCode()
    {
        return code;
    }

    public Object[] getArgs()
    {
        return args;
    }

    public String getDefaultMessage()
    {
        return defaultMessage;
    }
}