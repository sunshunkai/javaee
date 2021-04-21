package com.ssk.pulgin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;



/**
 * @author 孙顺凯（惊云）
 * @date 2021/4/21
 *
 * 示例插件：检查方法只能有一个参数,无参和两个及以上报错
 *
 */
@Mojo(name = "check")
public class CheckMojo extends AbstractMojo {


    public void execute() throws MojoExecutionException, MojoFailureException {


    }

}
