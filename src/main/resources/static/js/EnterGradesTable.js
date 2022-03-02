const buttons= [...document.querySelectorAll("[buttons-grade]")]
const students_div=[...document.getElementsByName("name")]
const inputs=[...document.querySelectorAll("[inputs]")]
const divs=[...document.querySelectorAll("[all-button-divs]")]
const inputDivs=[...document.getElementsByClassName("hidden")]
const returnButton=[...document.getElementsByClassName("return-button")]
var students=new Array

students_div.forEach(s => students.push(s.textContent))

console.log(returnButton)

buttons.forEach((b,index) =>{
    b.addEventListener("click",e => {
        if(b.classList.contains("selected")){
            b.classList.remove("selected")
        }else {
            OpenTextBox(index)
        }
        
    })
})

returnButton.forEach((b,index)=>{
    b.addEventListener("click",e =>{
        CloseTextBox(index)
    })
})

function OpenTextBox(inx) {
    let index=Math.floor(inx/5)
       
        setTimeout(function(){
            divs[index].classList.add("hidden")
        },100)

        inputs[index].value=buttons[inx].value

        setTimeout(function(){
            divs[index].classList.add("hide")
        },300)
        
        setTimeout(function() {
            inputDivs[index].classList.remove("hide")
        },400)

        setTimeout(function() {
            inputDivs[index].classList.remove("hidden")
        },500)
        
       
}

function CloseTextBox(index) {    
    setTimeout(function(){
        inputDivs[index].classList.add("hidden")
    },100)

    setTimeout(function(){
        inputDivs[index].classList.add("hide")
    },300)

    setTimeout(function() {
        divs[index].classList.remove("hide")
    },400)

    setTimeout(function() {
        divs[index].classList.remove("hidden")
    },500)
}

function getGrades() {
    let loop=0
    let index=0
    let grade =0
   var finalList=new Array
   console.log(grade)
  buttons.forEach((b) => {
      while(loop!==4){
          if(b.classList.contains("selected"))
          grade = b.value
         console.log(grade)
         loop++
      }
      if(grade!==0){
        finalList[index]=new Student(students[index],grade)
        console.log(finalList)
        index++
      }
        grade = 0
       loop=0
       
  })
}

class Student {
    constructor(name,grade){
        this.name=name
        this.grade=grade
    }
}