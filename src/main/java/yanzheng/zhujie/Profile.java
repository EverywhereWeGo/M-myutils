package yanzheng.zhujie;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Profile {
    /**
     * ID
     *
     * @return
     */
    public int id() default -1;

    /**
     * 身高
     *
     * @return
     */
    public int height() default 0;

    /**
     * 籍贯
     *
     * @return
     */
    public String nativePlace() default "";
}