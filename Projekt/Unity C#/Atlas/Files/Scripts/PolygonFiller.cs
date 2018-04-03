using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.EventSystems;

[ExecuteInEditMode]
public class PolygonFiller : MonoBehaviour {

	public Material matOn;
	public Material matOff;
	public Material matFail;
	private MeshRenderer renderer;
	private bool entered;
	private Main main;
	private int tick;
	private bool targeted;

	public Color onColor;
	public Color offColor;

	[HideInInspector]
	public bool blink;

	void Start () {
		PolygonCollider2D polygonCollider = GetComponent<PolygonCollider2D>();
		MeshFilter filter = GetComponent<MeshFilter>();
		renderer = GetComponent<MeshRenderer>();
		main = GameObject.FindWithTag("Main").GetComponent<Main>();

		Mesh mesh = new Mesh();
		Vector2[] points = polygonCollider.points;
		Vector3[] vertices = new Vector3[points.Length];
		//int[] triangles = new int[points.Length * 3];

		for(int i=0;i<points.Length;i++){
			points[i].Scale(new Vector2(1.1f,1.1f));
		}

		for(int i=0;i<points.Length;i++){
			vertices[i] = new Vector3(points[i].x, points[i].y, 0);
		}



		mesh.vertices = vertices;
		Triangulator t = new Triangulator(points);
		int[] triangles = t.Triangulate();
		renderer.material = matOff;
		mesh.triangles = triangles;
		//mesh.uv = uv;
		mesh.name = "Test";
		filter.mesh = mesh;
	}

	void Update(){
		if(blink){
			tick++;
			if(tick > 30)
				target();
			else
				untarget();
			if(tick >= 60)
				tick = 0;
		}
		/*if(Input.GetMouseButtonDown(0) && main.mouse.canHoverOverCountry() && !main.hamburgerMenu.activeInHierarchy){
			tick = 0;
			blink = false;
			untarget();
			Debug.Log("untargeted");
		}*/
	}

	/*void OnMouseEnter()
	{
		if(main.mouse.canHoverOverCountry()){
			this.renderer.material = matGreen;
		}
	}

	void OnMouseExit()
	{
		this.renderer.material = matRed;
	}*/

	public void target(){
		this.renderer.material = matOn;
		main.currentCountryTarget = this;
		targeted = true;
	}
	public void target(Material mat){
		this.renderer.material = mat;
		main.currentCountryTarget = this;
		targeted = true;
	}

	public void untarget(){
		this.renderer.material = matOff;
		targeted = false;
	}
	public IEnumerator wait(){
		yield return new WaitForSeconds(2);
		untarget();
	}
	public void warning(){
		this.renderer.material.color = Color.red;
	}
	public bool isTargeted(){
		return targeted;
	}
	public void startBlink(Material mat){
		blink = true;
		this.renderer.material = mat;
		Debug.Log(matOn.color);
	}
	public void stopBlink(){
		blink = false;
		untarget();
		tick = 0;
	}
}
