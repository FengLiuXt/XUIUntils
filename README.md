# XUIUntils
这个项目是关于UI相关的库。

界面管理：
  在com.example.xuiuntils.InterfaceManagement这个文件是包含了界面加载相关类。
  如果需要实现动态的加载模块，则需要在上层实现BaseData和ViewCenter这两个抽象类。
  
  BaseData：
    界面数据，只有一个字段number是模版编号。如过界面还需要扩展相关的字段请继承它，例如：
  public class TemplateData extends BaseData {

    private String title;
    private String subTitle;

    public TemplateData(String number){
        setNumber(number);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}

ViewCenter：这个是通过反射获取模版，例如：
  public class ViewDipather extends ViewCenter {

    private final String TEMPLATE_HEAD = "TemplateView";  //统一模版的头命名
    private int typebits = 3; //对于BaseData中的number取前3位来识别模版类型，如这个例子是取前3位。
    private final String COM = "000"; //com类型模板
    private final String SYS = "001"; //SYS类型模版

    public View resolver(Context context, String type) {
        View view = null;
        String sType;

        if((sType = getTemplateType(type)) != null){
            try {
            /**
            *这里通过反射要注意一定要添加你所有模版所在的包名，不然找不到。
            **/
                Class c = Class.forName("com.example.xuiuntils."+TEMPLATE_HEAD+sType+type.substring(typebits));
                Constructor constructor =c.getConstructor(Context.class);
                view = (View) constructor.newInstance(context);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                return view;
            }
        }

        return view;
    }
    
    /**
    *这个方法是通过传进来的模版编号，解析模版类型。
    *
    **/

    private String getTemplateType(String type){

        if(type.startsWith(COM))
            return "COM";
        else if(type.startsWith(SYS))
            return "SYS";
        else return null;
    }
}

模版编写规则：
注意：模版也就是view一定要实现BindData接口，例如：
  public class TemplateViewCOM0001 extends FrameLayout implements BindData<TemplateData> {

    TextView title;
    TextView subtitle;

    public TemplateViewCOM0001(Context context) {
        super(context);
        initView(context);
    }

    public TemplateViewCOM0001(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void initView(Context context){
        RelativeLayout relativeLayout = new RelativeLayout(context);
        addView(relativeLayout);

        RelativeLayout.LayoutParams layoutParams;

        ImageView imageView = new ImageView(context);
        imageView.setId(0);
        relativeLayout.addView(imageView);
        layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.width = 100;
        layoutParams.height = 100;
        imageView.setBackgroundColor(Color.YELLOW);

        title = new TextView(context);
        title.setId(1);
        relativeLayout.addView(title);
        layoutParams = (RelativeLayout.LayoutParams) title.getLayoutParams();
        layoutParams.addRule(RelativeLayout.RIGHT_OF, 0);

        subtitle = new TextView(context);
        relativeLayout.addView(subtitle);
        layoutParams = (RelativeLayout.LayoutParams) subtitle.getLayoutParams();
        layoutParams.addRule(RelativeLayout.BELOW, 1);
        layoutParams.addRule(RelativeLayout.RIGHT_OF, 0);

    }


    @Override
    public void initData(TemplateData templateData) {

        title.setText(templateData.getTitle());
        subtitle.setText(templateData.getSubTitle());
    }
}

使用：
   Adapter<TemplateData> adapter = new Adapter<>(this, list, new ViewDipather());
        mRecyclerView.setAdapter(adapter);
   




