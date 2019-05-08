
//1.List集合forEach遍历+Lambda表达式
productInfo.getChannels().forEach(channel -> channelList.add(channel));//只有一个语句

variableEnumPOS.forEach(variableEnumPO -> { 	//多条语句
                variableEnumPO.setId(null);
                variableEnumPO.setVariableVersionId(newVersionId);
                variableEnumMapper.insertSelective(variableEnumPO);
            });
			

//多个forEach嵌套
variables.forEach(variable -> {
	variable.getVersions().forEach(variableVersion -> {
		List<VariableEnum> variableEnums = new ArrayList<>();
		List<VariableEnumPO> variableEnumPOS = variableEnumMapper.getVariableEnum(variableVersion.getId());
		if (!CollectionUtils.isEmpty(variableEnumPOS)) {
			variableEnumPOS.forEach(variableEnumPO -> {
				variableEnums.add(variableEnumPOConverter.convertToTarget(variableEnumPO));
			});
		}
		variableVersion.setVariableEnums(variableEnums);
	});
});


//Map的forEach遍历
testCase.getResponseHeader().forEach((k, v) -> {
	String responseHeader = response.header(k.code());

	if (ValidatorTag.isValidatorTag(v)) {
		GenericValidator.validate(responseHeader, ValidatorTag.getEnumByCode(v));
	} else {
		assertEquals(responseHeader, v, "Expected header is not equal.");
	}
});

#=================================================================================================================	

/**
	2.@JsonValue 可以用在get方法或者属性字段上，一个类只能用一个，
	当加上@JsonValue注解时，序列化只返回这一个字段的值。
*/

#=================================================================================================================	

/**
	3.当从json创建java时,@JsonCreator注解的构造函数被会调用,如果没有这个注解,
	则默认会调用无参的构造函数但当你定义一个java类时 只定义了有参的构造函数,
	没有定义无参的构造函数时,这种场景你没用@JsonCreator注解会报错
*/

#=================================================================================================================	

// 4.枚举类
public enum ColorEnum {
    RED("red","红色"),
	GREEN("green","绿色"),
	BLUE("blue","蓝色");
	
    //防止字段值被修改，增加的字段也统一final表示常量
    private final String key;
    private final String value;
    
    private ColorEnum(String key,String value){
        this.key = key;
        this.value = value;
    }
    //根据key获取枚举
    public static ColorEnum getEnumByKey(String key){
        if(null == key){
            return null;
        }
        for(ColorEnum temp:ColorEnum.values()){
            if(temp.getKey().equals(key)){
                return temp;
            }
        }
        return null;
    }
    public String getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }
}
  
#=================================================================================================================	

//5.
CollectionUtils.isEmpty()//判断集合是否为空:
ObjectUtils.isEmpty()//对象判空
BooleanUtils.isTrue()//判断boolean类型
StringUtils.isEmpty()//判断字符串

#=================================================================================================================	

//6.流stream()操作

pos.stream().map(RuleTreeVariablePO::getVarVersionId).collect(Collectors.toList())//遍历
a = b.stream().filter(basic -> "basic的筛选条件,为返回boolean类型值得表达式").collect(Collectors.toList());//筛选

	
#=================================================================================================================	

//7.
equals方法来自于Object类
equalsIgnoreCase方法来自String类

equals对象参数是Object 用于比较两个对象是否相等
equals在Object类中方法默然比较对象内存地址,所有我们应该重写这个方法
equals在把对象放入HashMap中会被掉用

equalsIgnoreCase是String特有的方法
equalsIgnoreCase方法的参数是String对象,比较两个String对象是否相等(并且忽略大小写)

#=================================================================================================================	
	
//8.
ResponseEntity ：标识整个http相应：状态码、头部信息、响应体内容(spring)
@ResponseBody：加在请求处理方法上，能够处理方法结果值作为http响应体（springmvc）
@ResponseStatus：加在方法上、返回自定义http状态码(spring)

#=================================================================================================================		
//9.	
PO：persistent object持久对象（数据库逆向生成的对象,且对象每个属性对应数据表每一个字段）

1 ．有时也被称为Data对象，对应数据库中的entity，可以简单认为一个PO对应数据库中的一条记录。

2 ．在hibernate持久化框架中与insert/delet操作密切相关。

3 ．PO中不应该包含任何对数据库的操作。

 ---------------------------------------------------------

VO：value object值对象 / view object表现层对象（用于数据展示,但并不是数据表中对象每个字段都需要展示,
												 只保留需要展示的字段,将其封装成另一个对象即可看作VO）

1 ．主要对应页面显示（web页面/swt、swing界面）的数据对象。

2 ．可以和表对应，也可以不，这根据业务的需要。

 ----------------------------------------------------------
  
POJO ：plain ordinary java object 无规则简单java对象

一个中间对象，可以转化为PO、DTO、VO。

1 ．POJO持久化之后 == > PO

（在运行期，由Hibernate中的cglib动态把POJO转换为PO，PO相对于POJO会增加一些用来管理数据库entity状态的属性和方法。PO对于programmer来说完全透明，由于是运行期生成PO，所以可以支持增量编译，增量调试。）

2 ．POJO传输过程中 == > DTO

3 ．POJO用作表示层 == > VO



//10
Arrays.sort() 实现数组排序
Collections.sort() 实现集合排序

//11.Arrays工具类10大常用方法
 Arrays.toString(intArray) //打印数组
 Arrays.asList(stringArray)//利用数组创建集合
 ArrayUtils.addAll(intArray, intArray2);//合并两个数组
 sort(T[])//对数组的元素进行自然排序，要求元素必须实现了Comparable
 sort(T[],Comparator)//对数组的元素进行定制排序，元素本身可以不实现Comparable

//12.
@Async 注解(springboot异步任务)：,多人物之间不存在依赖关系时,用来解耦任务,提高程序运行效率
 	//任务1
    @Async
    public void doTaskOne() throws Exception {
        System.out.println("开始做任务一");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");
    }
  
    //任务2
    @Async
    public void doTaskTwo() throws Exception {
        System.out.println("开始做任务二");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务二，耗时：" + (end - start) + "毫秒");
    }
  
    //任务3
    @Async
    public void doTaskThree() throws Exception {
        System.out.println("开始做任务三");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务三，耗时：" + (end - start) + "毫秒");
    }
	
	//当加上注解时,这三个任务不会同步顺序执行,而是异步执行
	@RequestMapping("/task2")
    public String task2() throws Exception{
       task2.doTaskOne();
       task2.doTaskTwo();
       task2.doTaskThree();
       return"task2";
    }


//13.@JsonCreator
当从json创建java时,@JsonCreator注解的构造函数被会调用
如果没有这个注解,则默认会调用无参的构造函数


//14.Mybatis批处理
<foreach collection="dataTypes" item="varDataType" index="index" 
	open="(" close=")" separator=",">
	#{varDataType.code, jdbcType=VARCHAR}
</foreach>


//15.Mybatis连接查询
<resultMap id="VariableVersionOrg" type="com.oneconnect.sg.domain.Variable">
    <id column="id" jdbcType="BIGINT" property="id"/>
    <result column="org_id" jdbcType="BIGINT" property="organization.id"/>
    <result column="name" jdbcType="VARCHAR" property="name"/>
    <result column="code" jdbcType="VARCHAR" property="code"/>
    <result column="type" property="type"
            javaType="com.oneconnect.sg.common.constant.VarType"
            typeHandler="com.oneconnect.sg.common.handler.EnumTypeHandler"/>
    <result column="data_type" property="dataType"
            javaType="com.oneconnect.sg.common.constant.VarDataType"
            typeHandler="com.oneconnect.sg.common.handler.EnumTypeHandler"/>

    <collection property="versions" ofType="com.oneconnect.sg.domain.VariableVersion"
					javaType="java.util.List">
		<id column="version_id" jdbcType="BIGINT" property="id"/>
		<result column="version_no" jdbcType="VARCHAR" property="versionNo"/>
		<result column="category1_id" jdbcType="VARCHAR" property="category1.id"/>
		<result column="category1_name" jdbcType="VARCHAR" property="category1.name"/>
		<result column="category2_id" jdbcType="VARCHAR" property="category2.id"/>
		<result column="category2_name" jdbcType="VARCHAR" property="category2.name"/>
		<result column="description" jdbcType="VARCHAR" property="description"/>
		<result column="default_value" jdbcType="VARCHAR" property="defaultValue"/>
		<result column="rule_tree_id" jdbcType="VARCHAR" property="ruleTree.id"/>
		<result column="used_count" jdbcType="INTEGER" property="usedCount"/>
			<collection property="variableEnums" ofType="com.oneconnect.sg.domain.VariableEnum" javaType="java.util.List">
				<id column="variable_enum_id" jdbcType="BIGINT" property="id"></id>
				<result column="variable_enum_code" jdbcType="VARCHAR" property="code"/>
				<result column="variable_enum_description" jdbcType="VARCHAR" property="description"/>
			</collection>
    </collection>
 </resultMap>
 
 
 //16.批量插入
 <insert id="insertAuthor" useGeneratedKeys="true" keyProperty="id">
	insert into Author (username, password, email, bio) values
	<foreach item="item" collection="list" separator=",">
		0(#{item.username}, #{item.password}, #{item.email}, #{item.bio})
	</foreach>
</insert>


//17.批量删除
<delete id="deleteHistory">
	DELETE FROM
	product_application_config_history
	WHERE (product_no,channel_id,org_id) in
	(select product_no,channel_id,org_id from product_application_config where
	decision_flow_id is not null
	AND template_id is not null and
	id in
	<foreach collection="IdList" item="item" index="index"
			 open="(" close=")" separator=",">
		#{item, jdbcType=VARCHAR}
	</foreach>
	)
</delete>

//18. HttpServletResponse
设置响应头信息；addHeader(“reFresh”, “5;URL=xxxx”);
发送状态码；sendError(404);
设置响应正文；getWriter().print(“fdsfdsa”);
重定向：sendRedirect(“path”);

2　设置状态码及其他方法
response.setContentType("text/html;charset=utf-8")：设置响应类型为html，编码为utf-8，处理相应页面文本显示的乱码；
response.setCharacterEncoding(“utf-8”)：如果响应类型为文本，那么就需要设置文本的编码类型，然后浏览器使用这个编码来解读文本。
										注意，如果没有设置contentType，那么浏览器会认为contentType为text/html，
										如果没设置编码，那么默认为ISO-8859-1编码。
										所以以上两点在使用response返回结果之前必须设置。
response.setStatus(200)：设置状态码；
response.sendError(404, “您要查找的资源不存在”)：当发送错误状态码时，Tomcat会跳转到固定的错误页面去，但可以显示错误信息。

3　设置响应头信息
response.setHeader(“contentType”, “text/html;charset=utf-8”)：与setContentType()方法的功能相同。setContentType()方法属于便捷方法；

刷新（定时重定向）：
response.setHeader("Refresh","5; URL=http://www.baidu.com")：5秒后自动跳转到百度主页。


//19.
2.1、获得客户机信息
　　getRequestURL方法返回客户端发出请求时的完整URL。
　　getRequestURI方法返回请求行中的资源名部分。
　　getQueryString 方法返回请求行中的参数部分。
　　getPathInfo方法返回请求URL中的额外路径信息。额外路径信息是请求URL中的位于Servlet的路径之后和查询参数之前的内容，它以“/”开头。
　　getRemoteAddr方法返回发出请求的客户机的IP地址。
　　getRemoteHost方法返回发出请求的客户机的完整主机名。
　　getRemotePort方法返回客户机所使用的网络端口号。
　　getLocalAddr方法返回WEB服务器的IP地址。
　　getLocalName方法返回WEB服务器的主机名。

2.2、获得客户机请求头
　　getHeader(string name)方法:String 
　　getHeaders(String name)方法:Enumeration 
　　getHeaderNames()方法

2.3、获得客户机请求参数(客户端提交的数据)
getParameter(String)方法(常用)
getParameterValues(String name)方法(常用)
getParameterNames()方法(不常用)
getParameterMap()方法(编写框架时常用)

/*
  20.权限验证流程
*/
//1. 获取当前登录用户 -> 获取请求接口上@Permission注解里面的code权限值
PermissionUtil.checkUserStatusAndFunctionAccess(ContextUtil.getUser(),
												((HandlerMethod) handler).getMethod().getAnnotation(Permission.class));

//2. 验证用户
//2.1验证用户是否激活 validateUserStatus(user);
	public static void validateUserStatus(User user) {
		if (user == null) {
			throw new UnauthorizedException(ErrorCodeConstant.UNAUTHORIZED);
		}
		//验证当前用户状态
		if (UserStatus.INACTIVE.equals(user.getStatus())) {
			throw new PermissionDeniedException(ErrorCodeConstant.INACTIVE_USER);
		//验证当前用户organization
		} else if (user.getOrganization() == null ||
				//验证organization等级
				user.getOrganization().getHierarchy() == null ||
				//验证organization状态 
				!OrgStatus.ACTIVE.equals(user.getOrganization().getStatus())) {
			throw new PermissionDeniedException(ErrorCodeConstant.USER_ORGANIZATION_INACTIVE, "User org is invalid or inactive");
		}
	}
//2.2验证用户接口权限,hasFunctionAccess(user, permission);
	hasFunctionAccess(user, permission);
	private static void hasFunctionAccess(User user, Permission permission) {
		//验证是否有@Permission注解
		if (CollectionUtils.isEmpty(user.getRoles()) || permission == null) {
			throw new PermissionDeniedException("Empty role or Missing @Permission");
		}
		//验证@Permission注解里面的bypass属性值,true则跳过接口验证,默认为false
		if (permission.bypass()) {
			return;
		}

		Set<Action> actions = new HashSet<>();
		//获取用户所有的角色
		user.getRoles().stream().forEach(
				role -> {
					//获取用户角色对应的所有权限集合
					for (Function f : role.getFunctions()) {
						//通过用户权限code获取该权限对应的FnCode枚举类对象,PermissionMapper以此枚举类对象为key
						//通过登陆用户权限对应的枚举类对象,从初始化的权限Map(PERMISSION_MAP)中获取用户对应的接口
						//权限集合列表。
						actions.addAll(Arrays.asList(PermissionMapper.map(FnCode.getEnumByCode(f.getCode()))));
					}
				}
		);

		//由上一步获取的登陆用户的权限集合列表,与用户请求接口@Permission注解中code权限码一一比对
		//存在则继续执行业务逻辑,否则抛出"User has no function access"异常
		for (Action action : permission.code()) {
			if (actions.contains(action)) {
				return;
			}
		}

		throw new PermissionDeniedException("User has no function access");
	}



/*
  21. JSON对象的互相转换(jackson)
*/
public class JacksonUtil {

    private static ObjectMapper objectMapper;

    /**
     * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
     * 转换为普通JavaBean：readValue(json,Student.class)
     * 转换为List:readValue(json,List.class).readValue(json,List.class)返回其实是List<Map>类型，第二个参数不能是List<Student>.class，所以不能直接转换。
     * 转换为特定类型的List，比如List<Student>，把readValue()的第二个参数传递为Student[].class.然后使用Arrays.asList();方法把得到的数组转换为特定类型的List。
     * 转换为Map：readValue(json,Map.class)
     * 我们使用泛型，得到的也是泛型
     *
     * @param content   原始json字符串数据
     * @param valueType 要转换的JavaBean类型
     * @return JavaBean对象
     */
    public static <T> T readValue(String content, Class<T> valueType) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        try {
            //字符串转Json对象
            return objectMapper.readValue(content, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static <T> T readValue(String content, TypeReference<T> typeReference) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        try {
            //字符串转Json对象
            return objectMapper.readValue(content, typeReference);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 把JavaBean转换为json字符串
     * 普通对象转换：toJson(Student)
     * List转换：    toJson(List)
     * Map转换:      toJson(Map)
     * 我们发现不管什么类型，都可以直接传入这个方法
     *
     * @param object JavaBean对象
     * @return json字符串
     */
    public static String toJson(Object object) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        try {
            //Json对象转为String字符串
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}


/*
	22.springcloud gateway配置
*/
server:
  port: 8766

spring:
  application:
    name: service-gateway
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true #在eureka中，服务是以大写的形式注册的，可以转化成小写
      routes:
        - id: service-client       # 服务唯一ID标识
          uri: lb://service-client # 注册中心的服务id,路有其他服务要用lb://
          predicates:
            - Path=/client/** #请求转发
          filters:
            - StripPrefix=1 #切割请求，去除/client/

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/


/*
	23.
*/

registry.addInterceptor(commonContextInterceptor)
		.order(Ordered.HIGHEST_PRECEDENCE);//order(Ordered.HIGHEST_PRECEDENCE)表示该拦截器在所有注册的拦截器中级别最高,优先执行


/*
	24.
*/
 git commit：是将本地修改过的文件提交到本地库中；
 git push：是将本地库中的最新信息发送给远程库；
 git pull：是从远程获取最新版本到本地，并自动merge；
 git fetch：是从远程获取最新版本到本地，不会自动merge；
 git merge：是用于从指定的commit(s)合并到当前分支，用来合并两个分支；


 /*
	25.MyBatis
 */

	<result column="data_type" property="dataType"
		javaType="com.oneconnect.sg.common.constant.VarDataType"
		typeHandler="com.oneconnect.sg.common.handler.EnumTypeHandler"/>
	//JavaType和ofType都是用来指定对象类型的,但是JavaType是用来指定pojo中属性的类型,而ofType指定的是映射到list集合属性中pojo的类型。
	<collection property="versions" ofType="com.oneconnect.sg.domain.VariableVersion"
				javaType="java.util.List">
		<id column="version_id" jdbcType="BIGINT" property="id"/>
		<result column="version_no" jdbcType="VARCHAR" property="varId"/>
		
		<association property="category" javaType="com.oneconnect.sg.domain.Category">
			<result column="category2_id" jdbcType="VARCHAR" property="id"/>
			<result column="category2_name" jdbcType="VARCHAR" property="name"/>
			<association property="parent" javaType="com.oneconnect.sg.domain.Category">
				<result column="category1_id" jdbcType="VARCHAR" property="id"/>
				<result column="category1_name" jdbcType="VARCHAR" property="name"/>
			</association>
		</association>
		
		<collection property="variableEnums" ofType="com.oneconnect.sg.domain.VariableEnum"
					javaType="java.util.List">
			<id column="variable_enum_id" jdbcType="BIGINT" property="id"></id>
			<result column="variable_enum_code" jdbcType="VARCHAR" property="code"/>
			<result column="variable_enum_description" jdbcType="VARCHAR" property="description"/>
		</collection>
	</collection>



/*
	26.自定义注解
*/

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER})
@Documented
//@Constraint注解指定自定义注解执行逻辑的类的路径
@Constraint(validatedBy = NotBlank.NotBlankValidator.class)
public @interface NotBlank {
    ErrorCodeConstant errorCode() default ErrorCodeConstant.SYSTEM_ERROR;

    String message() default "invalid input";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

	/* 
		自定义注解执行逻辑 
		这个自定义注解逻辑处理类由于实现了ConstraintValidator接口，所以它默认被spring管理成bean,
		所以可以在这个逻辑处理类里面用@Autowiredu或者@Resources注入别的服务，而且不用在类上面用@Compent注解成spring的bean.
		
		自定义验证类型注解类里面由于是用于验证数据,一般在里面加上
		String message() default "用户不存在或者不属于当前组织";
		Class<?>[] groups() default {};
		Class<? extends Payload>[] payload() default {};

	*/
    class NotBlankValidator implements ConstraintValidator<NotBlank, Object> {
        private ErrorCodeConstant errorCode;

        @Override
        public void initialize(NotBlank constraintAnnotation) {
            this.errorCode = constraintAnnotation.errorCode();
        }

        @Override
        public boolean isValid(Object obj, ConstraintValidatorContext context) {
            if (!StringUtils.isBlank(obj.toString())) {
                return true;
            }
            throw new InvalidInputException(errorCode);
        }
    }
}

